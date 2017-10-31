package ru.atc.bclient.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.AccountStatus;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.model.entity.PaymentOrderStatus;
import ru.atc.bclient.service.AccountService;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.service.LegalEntityService;
import ru.atc.bclient.service.PaymentOrderProcessor;
import ru.atc.bclient.service.PaymentOrderService;
import ru.atc.bclient.web.dto.Notification;
import ru.atc.bclient.web.dto.NotificationType;
import ru.atc.bclient.web.dto.PaymentOrderFormData;
import ru.atc.bclient.web.security.AuthorizedUser;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;

import static ru.atc.bclient.web.controller.PaymentOrderController.PATH;

@Controller
@RequestMapping(PATH)
@Slf4j
public class PaymentOrderController extends AbstractController {
    static final String PATH = "/payment";

    private PaymentOrderService paymentOrderService;
    private LegalEntityService legalEntityService;
    private AccountService accountService;
    private ContractService contractService;
    private PaymentOrderProcessor paymentOrderProcessor;

    public PaymentOrderController(PaymentOrderService pos, LegalEntityService les, AccountService as, ContractService cs,
                                  PaymentOrderProcessor processor) {
        this.paymentOrderService = pos;
        this.legalEntityService = les;
        this.accountService = as;
        this.contractService = cs;
        this.paymentOrderProcessor = processor;
    }

    @GetMapping
    public String getAll(Model model, HttpSession session, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        LocalDate startDate = (LocalDate) session.getAttribute(ATTR_DATE_START);
        LocalDate endDate = (LocalDate) session.getAttribute(ATTR_DATE_END);
        LocalDate currentDate = LocalDate.now();
        if (startDate == null && endDate == null) {
            startDate = currentDate;
            endDate = currentDate;
        } else if (endDate == null) {
            endDate = startDate.isBefore(currentDate) ? currentDate : startDate;
        } else if (startDate == null) {
            startDate = endDate.isAfter(currentDate) ? currentDate : endDate;
        } else if (startDate.isAfter(endDate)) {
            endDate = startDate;
        }
        model.addAttribute(ATTR_DATE_START, startDate);
        model.addAttribute(ATTR_DATE_END, endDate);
        model.addAttribute(ATTR_PAYMENT_ORDERS,
                paymentOrderService.getAllBySendersGroupByLegalEntityAndAccount(startDate, endDate, authorizedUser.getLegalEntities()));
        return "paymentOrders";
    }

    @PostMapping("filter")
    public String filter(HttpSession session,
                         @RequestParam(value = ATTR_DATE_START)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                         @RequestParam(value = ATTR_DATE_END)
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        session.setAttribute(ATTR_DATE_START, startDate);
        session.setAttribute(ATTR_DATE_END, endDate);
        return REDIRECT + PATH;
    }

    @GetMapping("view")
    public String view(Model model, RedirectAttributes redirectAttributes,
                       @RequestParam Integer id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        PaymentOrder paymentOrder = paymentOrderService.getBySendersAndId(authorizedUser.getLegalEntities(), id);
        if (paymentOrder == null) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, "Ошибка: платежное поручение не найдено."));
            return REDIRECT + PATH;
        }
        model.addAttribute(ATTR_PAYMENT_ORDER, paymentOrder);
        return "paymentOrderView";
    }

    @PostMapping
    public String create(Model model, RedirectAttributes redirectAttributes,
                         HttpSession session,
                         @RequestParam Integer senderId, @RequestParam Integer senderAccountId,
                         @AuthenticationPrincipal AuthorizedUser authorizedUser) {

        if (paymentOrderProcessor.isProcessingInProgress()) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.WARNING,
                            MESSAGE_OPERATION_CREATE + MESSAGE_DENIED_OPERATION + MESSAGE_PROCESSING_IN_PROGRESS));
            return REDIRECT + PATH;
        }

        PaymentOrder paymentOrder = new PaymentOrder();
        LegalEntity sender = legalEntityService.get(senderId);
        Account senderAccount = accountService.get(senderAccountId);

        StringBuilder errorMessage = new StringBuilder();

        if (sender == null || !authorizedUser.getLegalEntities().contains(sender)) {
            errorMessage.append("Отправитель не найден.<br/>");
        } else {
            if (!sender.getAccounts().contains(senderAccount)) {
                errorMessage.append("Счет отправителя принадлежит другому юридическому лицу.<br/>");
            }
            if (!senderAccount.getStatus().equals(AccountStatus.ACTIVE)) {
                errorMessage.append("Счет отправителя не активен.<br/>");
            }
        }

        if (errorMessage.length() > 0) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER + errorMessage));
            return REDIRECT + PATH;
        }

        paymentOrder.setSender(sender);
        paymentOrder.setSenderAccount(senderAccount);
        paymentOrder.setCurrencyCode(senderAccount.getCurrencyCode());
        paymentOrder.setDate(LocalDate.now());
        paymentOrder.setNumber(paymentOrderService.getNewNumber(sender));
        paymentOrder.setStatus(PaymentOrderStatus.NEW);

        session.setAttribute(ATTR_PAYMENT_ORDER, paymentOrder);

        model.addAttribute(ATTR_CONTRACTS, contractService.getAllActive(sender, senderAccount.getCurrencyCode()));
        model.addAttribute(ATTR_PAYMENT_ORDER, paymentOrder);
        model.addAttribute(ATTR_PAYMENT_ORDER_FORM_DATA, new PaymentOrderFormData());
        return "paymentOrderEdit";
    }

    @PostMapping("save")
    public String save(Model model, RedirectAttributes redirectAttributes, HttpSession session,
                       @Valid @ModelAttribute PaymentOrderFormData paymentOrderFormData, BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();

        PaymentOrder paymentOrder = (PaymentOrder) session.getAttribute(ATTR_PAYMENT_ORDER);

        if (paymentOrder == null) {
            errorMessage.append(" ");
        } else if (paymentOrder.getSenderAccount() == null) {
            errorMessage.append("Счет отправителя не найден.");
        } else if (!paymentOrder.getSenderAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            errorMessage.append("Счет отправителя не активен.");
        }

        if (errorMessage.length() > 0) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER + errorMessage));
            return REDIRECT + PATH;
        }

        assert paymentOrder != null;

        if (paymentOrderProcessor.isProcessingInProgress()) {
            model.addAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.WARNING,
                            MESSAGE_OPERATION_SAVE + MESSAGE_DENIED_OPERATION + MESSAGE_PROCESSING_IN_PROGRESS));
            model.addAttribute(ATTR_CONTRACTS, contractService.getAllActive(paymentOrder.getSender(),
                    paymentOrder.getSenderAccount().getCurrencyCode()));
            model.addAttribute(ATTR_PAYMENT_ORDER, paymentOrder);
            model.addAttribute(ATTR_PAYMENT_ORDER_FORM_DATA, paymentOrderFormData);
            return "paymentOrderEdit";
        }

        Integer recipientId = paymentOrderFormData.getRecipientId();
        Account recipientAccount = null;
        LegalEntity recipient = null;
        if (recipientId != null) {
            recipient = legalEntityService.get(recipientId);
            if (recipient == null) {
                errorMessage.append("Получатель платежа не найден.<br/>");
            } else {
                paymentOrder.setRecipient(recipient);
                recipientAccount = validateRecipientAccount(errorMessage, paymentOrder, paymentOrderFormData.getRecipientAccountId());
            }
        }

        Contract contract = null;
        Integer contractId = paymentOrderFormData.getContractId();
        if (contractId != null) {
            contract = validateContract(errorMessage, paymentOrder, contractId);
        }

        errorMessage.append(getFieldErrorMessages(bindingResult.getFieldErrors()));

        if (errorMessage.length() > 0) {
            model.addAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR,
                            "<strong>При сохранении платежного поручения произошли следующие ошибки:</strong><br/>"
                                    + errorMessage.toString()));
            model.addAttribute(ATTR_CONTRACTS, contractService.getAllActive(paymentOrder.getSender(),
                    paymentOrder.getSenderAccount().getCurrencyCode()));
            model.addAttribute(ATTR_PAYMENT_ORDER, paymentOrder);
            model.addAttribute(ATTR_PAYMENT_ORDER_FORM_DATA, paymentOrderFormData);
            return "paymentOrderEdit";
        }

        paymentOrder.setRecipientAccount(recipientAccount);
        paymentOrder.setContract(contract);
        paymentOrder.setAmount(paymentOrderFormData.getAmount());
        paymentOrder.setPriorityCode(paymentOrderFormData.getPriorityCode());
        paymentOrder.setReason(paymentOrderFormData.getReason());
        paymentOrder.setStatus(PaymentOrderStatus.ACCEPTED);

        try {
            paymentOrderService.save(paymentOrder);
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.SUCCESS, "Платежное поручение успешно создано."));
            session.removeAttribute(ATTR_PAYMENT_ORDER);
        } catch (Exception e) {
            log.error("Error saving payment order " + paymentOrder, e);
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER));
        }
        return REDIRECT + PATH;
    }

    private Account validateRecipientAccount(StringBuilder errorMessage, PaymentOrder paymentOrder, Integer recipientAccountId) {
        Account recipientAccount = recipientAccountId == null ? null : accountService.get(recipientAccountId);
        if (recipientAccount == null) {
            errorMessage.append("Счет получателя платежа не найден.<br/>");
        } else {
            if (!paymentOrder.getRecipient().getAccounts().contains(recipientAccount)) {
                errorMessage.append("Счет получателя платежа принадлежит другому юридическому лицу.<br/>");
            }
            if (!recipientAccount.getStatus().equals(AccountStatus.ACTIVE)) {
                errorMessage.append("Счет получателя не активен.<br/>");
            }
            if (recipientAccount.equals(paymentOrder.getSenderAccount())) {
                errorMessage.append("Счета отправителя и получателя совпадают.<br/>");
            }
            if (!paymentOrder.getSenderAccount().getCurrencyCode().equals(recipientAccount.getCurrencyCode())) {
                errorMessage.append("Валюты счетов отправителя и получателя не совпадают.<br/>");
            }
            if (!paymentOrder.getCurrencyCode().equals(recipientAccount.getCurrencyCode())) {
                errorMessage.append("Валюты платежного поручения и счета получателя не совпадают.<br/>");
            }
        }
        return recipientAccount;
    }

    private Contract validateContract(StringBuilder errorMessage, PaymentOrder paymentOrder, Integer contractId) {
        Contract contract = null;
        if (contractId == null || (contract = contractService.get(contractId)) == null) {
            errorMessage.append("Договор не найден.<br/>");
        } else {
            if (!contract.getIssuer().equals(paymentOrder.getSender())) {
                errorMessage.append("Отправитель не совпадает с эмитентом договора.<br/>");
            }
            if (!contract.getSigner().equals(paymentOrder.getRecipient())) {
                errorMessage.append("Получатель не совпадает с подписантом договора.<br/>");
            }
            if (!contract.getCurrencyCode().equals(paymentOrder.getSenderAccount().getCurrencyCode())) {
                errorMessage.append("Валюта счета отправителя не совпадает с валютой договора.<br/>");
            }
            if (!contract.getCurrencyCode().equals(paymentOrder.getRecipientAccount().getCurrencyCode())) {
                errorMessage.append("Валюта счета получателя не совпадает с валютой договора.<br/>");
            }
            if (!contract.getCloseDate().isAfter(LocalDate.now())) {
                errorMessage.append("Дата окончания действия договора меньше текущей.<br/>");
            }
            if (!contract.getOpenDate().isBefore(LocalDate.now())) {
                errorMessage.append("Дата начала действия договора больше текущей.<br/>");
            }
        }
        return contract;
    }

    @PostMapping("cancel")
    public String cancel(@RequestParam Integer id, RedirectAttributes redirectAttributes,
                         @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Notification notification;
        PaymentOrder paymentOrder = paymentOrderService.get(id);
        if (paymentOrder == null || !authorizedUser.getLegalEntities().contains(paymentOrder.getSender())) {
            notification = new Notification(NotificationType.ERROR, "Платежное поручение не найдено.");
        } else if (!(paymentOrder.getStatus().equals(PaymentOrderStatus.NEW) || paymentOrder.getStatus().equals(PaymentOrderStatus.ACCEPTED))) {
            notification = new Notification(NotificationType.ERROR, "Отмена платежного поручения запрещена.");
        } else if (paymentOrderProcessor.isProcessingInProgress()) {
            notification = new Notification(NotificationType.WARNING,
                    MESSAGE_OPERATION_UPDATE + MESSAGE_DENIED_OPERATION + MESSAGE_PROCESSING_IN_PROGRESS);
        } else {
            try {
                paymentOrder.setStatus(PaymentOrderStatus.CANCELLED);
                paymentOrderService.save(paymentOrder);
                notification = new Notification(NotificationType.SUCCESS, "Платежное поручение успешно отменено.");
            } catch (Exception e) {
                log.error("Error saving payment order " + paymentOrder, e);
                notification = new Notification(NotificationType.ERROR, MESSAGE_ERROR_CANCELLING_PAYMENT_ORDER);
            }
        }
        redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION, notification);
        return REDIRECT + PATH;
    }

    @GetMapping("reset")
    public String reset(HttpSession session) {
        session.removeAttribute(ATTR_PAYMENT_ORDER);
        return REDIRECT + PATH;
    }

    @GetMapping("process")
    public String process() {
        paymentOrderProcessor.process();
        return REDIRECT + PATH;
    }
}
