package ru.atc.bclient.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import ru.atc.bclient.service.PaymentOrderService;
import ru.atc.bclient.web.security.AuthorizedUser;
import ru.atc.bclient.web.to.Notification;
import ru.atc.bclient.web.to.NotificationType;
import ru.atc.bclient.web.to.PaymentOrderFormData;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;

import static ru.atc.bclient.web.controller.CommonStringConstants.*;

@Controller
@RequestMapping("/payment")
@Slf4j
public class PaymentOrderController {
    private static final String ATTRIBUTE_PAYMENT_ORDER_MAP = "paymentOrderMap";
    private static final String MESSAGE_ERROR_CREATING_PAYMENT_ORDER = "Ошибка создания платежного поручения!<br/>";
    private static final String MESSAGE_ERROR_CANCELLING_PAYMENT_ORDER = "Ошибка отмены платежного поручения!<br/>";

    private PaymentOrderService paymentOrderService;
    private LegalEntityService legalEntityService;
    private AccountService accountService;
    private ContractService contractService;

    public PaymentOrderController(PaymentOrderService paymentOrderService, LegalEntityService legalEntityService, AccountService accountService, ContractService contractService) {
        this.paymentOrderService = paymentOrderService;
        this.legalEntityService = legalEntityService;
        this.accountService = accountService;
        this.contractService = contractService;
    }

    @GetMapping
    public String getAll(Model model,
                                   @RequestParam(value = ATTRIBUTE_START_DATE, required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate starDate,
                                   @RequestParam(value = ATTRIBUTE_END_DATE, required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        LocalDate currentDate = LocalDate.now();
        if (starDate == null && endDate == null) {
            starDate = currentDate;
            endDate = currentDate;
        } else if (endDate == null) {
            endDate = starDate.isBefore(currentDate) ? currentDate : starDate;
        } else if (starDate == null) {
            starDate = endDate.isAfter(currentDate) ? currentDate : endDate;
        } else if (starDate.isAfter(endDate)) {
            endDate = starDate;
        }
        model.addAttribute(ATTRIBUTE_START_DATE, starDate);
        model.addAttribute(ATTRIBUTE_END_DATE, endDate);
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER_MAP,
                paymentOrderService.getAllBySendersGroupByLegalEntityAndAccount(starDate, endDate, authorizedUser.getLegalEntities()));
        return "paymentOrders";
    }

    @GetMapping("view")
    public String view(Model model, RedirectAttributes redirectAttributes,
                                   @RequestParam Integer id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        PaymentOrder paymentOrder = paymentOrderService.getBySendersAndId(authorizedUser.getLegalEntities(), id);
        if (paymentOrder == null) {
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR, "Ошибка: платежное поручение не найдено."));
            return "redirect:/payment";
        }
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER, paymentOrder);
        return "paymentOrderView";
    }

    @PostMapping
    public String create(Model model, RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     @RequestParam Integer senderId, @RequestParam Integer senderAccountId,
                                     @AuthenticationPrincipal AuthorizedUser authorizedUser) {
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
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER + "<br/>" + errorMessage));
            return "redirect:/payment";
        }

        paymentOrder.setSender(sender);
        paymentOrder.setSenderAccount(senderAccount);
        paymentOrder.setCurrencyCode(senderAccount.getCurrencyCode());
        paymentOrder.setDate(LocalDate.now());
        paymentOrder.setNumber(paymentOrderService.getNewNumber(sender));
        paymentOrder.setStatus(PaymentOrderStatus.NEW);

        session.setAttribute(ATTRIBUTE_PAYMENT_ORDER, paymentOrder);

        model.addAttribute(ATTRIBUTE_CONTRACTS, contractService.getAllActive(sender, senderAccount.getCurrencyCode()));
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER, paymentOrder);
        model.addAttribute(ATTRIBUTE_PAYMENT_ORDER_TO, new PaymentOrderFormData());
        return "paymentOrderEdit";
    }

    @PostMapping("save")
    public String save(Model model, RedirectAttributes redirectAttributes,
                                   HttpSession session,
                                   @Valid @ModelAttribute PaymentOrderFormData paymentOrderFormData, BindingResult result) {

        StringBuilder errorMessage = new StringBuilder();

        PaymentOrder paymentOrder = (PaymentOrder) session.getAttribute(ATTRIBUTE_PAYMENT_ORDER);

        if (paymentOrder == null) {
            errorMessage.append(" ");
        } else if (paymentOrder.getSenderAccount() == null) {
            errorMessage.append("Счет отправителя не найден.");
        } else if (!paymentOrder.getSenderAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            errorMessage.append("Счет отправителя не активен.");
        }

        if (errorMessage.length() > 0) {
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER + errorMessage));
            return "redirect:/payment";
        }

        assert paymentOrder != null;

        Integer recipientId = paymentOrderFormData.getRecipientId();
        Account recipientAccount = null;
        LegalEntity recipient = null;
        if (recipientId != null) {
            recipient = legalEntityService.get(recipientId);
            if (recipient == null) {
                errorMessage.append("Получатель платежа не найден.<br/>");
            } else {
                Integer recipientAccountId = paymentOrderFormData.getRecipientAccountId();
                if (recipientAccountId == null) {
                    errorMessage.append("Счет получателя платежа не найден.<br/>");
                } else {
                    recipientAccount = accountService.get(recipientAccountId);
                    if (recipientAccount == null) {
                        errorMessage.append("Счет получателя платежа не найден.<br/>");
                    } else {
                        if (!recipient.getAccounts().contains(recipientAccount)) {
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
                }
            }
        }

        Integer contractId = paymentOrderFormData.getContractId();
        Contract contract = null;
        if (contractId != null) {
            contract = contractService.get(contractId);
            if (contract == null) {
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
        }

        if (result.hasErrors()) {
            for (FieldError error : result.getFieldErrors()) {
                errorMessage.append("Поле \"").append(error.getField()).append("\" ")
                        .append(error.getDefaultMessage()).append(".<br/>");
            }
        }

        if (errorMessage.length() > 0) {
            model.addAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR,
                            "<strong>При сохранении платежного поручения произошли следующие ошибки:</strong><hr/>"
                                    + errorMessage.toString()));
            model.addAttribute(ATTRIBUTE_CONTRACTS, contractService.getAllActive(paymentOrder.getSender(),
                    paymentOrder.getSenderAccount().getCurrencyCode()));
            model.addAttribute(ATTRIBUTE_PAYMENT_ORDER, paymentOrder);
            model.addAttribute(ATTRIBUTE_PAYMENT_ORDER_TO, paymentOrderFormData);
            return "paymentOrderEdit";
        }

        paymentOrder.setRecipient(recipient);
        paymentOrder.setRecipientAccount(recipientAccount);
        paymentOrder.setContract(contract);
        paymentOrder.setAmount(paymentOrderFormData.getAmount());
        paymentOrder.setPriorityCode(paymentOrderFormData.getPriorityCode());
        paymentOrder.setReason(paymentOrderFormData.getReason());
        paymentOrder.setStatus(PaymentOrderStatus.ACCEPTED);

        try {
            paymentOrderService.save(paymentOrder);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.SUCCESS, "Платежное поручение успешно создано."));
        } catch (Exception e) {
            log.error("Error saving payment order " + paymentOrder, e);
            redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION,
                    new Notification(NotificationType.ERROR, MESSAGE_ERROR_CREATING_PAYMENT_ORDER));
            return "redirect:/payment";
        }
        session.removeAttribute(ATTRIBUTE_PAYMENT_ORDER);
        return "redirect:/payment";
    }

    @PostMapping("cancel")
    public String cancel(@RequestParam Integer id,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Notification notification;
        PaymentOrder paymentOrder = paymentOrderService.get(id);
        if (paymentOrder == null
                || !authorizedUser.getLegalEntities().contains(paymentOrder.getSender())) {
            notification = new Notification(NotificationType.ERROR, "Платежное поручение не найдено.");
        } else if (!(paymentOrder.getStatus().equals(PaymentOrderStatus.NEW) || paymentOrder.getStatus().equals(PaymentOrderStatus.ACCEPTED))) {
            notification = new Notification(NotificationType.ERROR, "Отмена платежного поручения запрещена.");
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
        redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION, notification);
        return "redirect:/payment";
    }

    @GetMapping("reset")
    public String reset(HttpSession session) {
        session.removeAttribute(ATTRIBUTE_PAYMENT_ORDER);
        return "redirect:/payment";
    }
}
