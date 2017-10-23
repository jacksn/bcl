package ru.atc.bclient.web.controller;

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
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.service.LegalEntityService;
import ru.atc.bclient.web.security.AuthorizedUser;
import ru.atc.bclient.web.to.Notification;
import ru.atc.bclient.web.to.NotificationType;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/contract")
public class ContractController {
    public static final String ATTRIBUTE_LEGAL_ENTITIES = "legalEntities";
    public static final String ATTRIBUTE_CONTRACT = "contract";
    public static final String ATTRIBUTE_NOTIFICATION = "notification";

    private ContractService contractService;
    private LegalEntityService legalEntityService;

    public ContractController(ContractService contractService, LegalEntityService legalEntityService) {
        this.contractService = contractService;
        this.legalEntityService = legalEntityService;
    }

    @GetMapping
    public String getContracts(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute("contractMap",
                contractService.getAllByIssuersGroupByLegalEntity(authorizedUser.getLegalEntities()));
        return "contracts";
    }

    @PostMapping
    public String createContract(Model model, HttpSession session, RedirectAttributes redirectAttributes,
                                 @RequestParam Integer issuerId,
                                 @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Contract contract = (Contract) session.getAttribute(ATTRIBUTE_CONTRACT);
        if (contract == null) {
            contract = new Contract();
            LegalEntity legalEntity = legalEntityService.getById(issuerId);
            if (legalEntity == null || !authorizedUser.getLegalEntities().contains(legalEntity)) {
                redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION, new Notification(NotificationType.ERROR, "Ошибка создания договора"));
                return "redirect:/contract";
            }
            contract.setIssuer(legalEntity);
            contract.setOpenDate(LocalDate.now());
            contract.setCloseDate(LocalDate.now().plus(1, ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS));
            session.setAttribute(ATTRIBUTE_CONTRACT, contract);
        }
        model.addAttribute(ATTRIBUTE_CONTRACT, contract);
        model.addAttribute(ATTRIBUTE_LEGAL_ENTITIES, legalEntityService.getAll());
        return "contractEdit";
    }

    @PostMapping("/save")
    public String saveContract(Model model,
                               @Valid @ModelAttribute Contract contract,
                               RedirectAttributes redirectAttributes,
                               BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder message = new StringBuilder();
            for (FieldError error : result.getFieldErrors()) {
                message.append("Поле ")
                        .append(error.getField())
                        .append(" ")
                        .append(error.getDefaultMessage())
                        .append(".<br/>");
            }
            model.addAttribute(ATTRIBUTE_NOTIFICATION, new Notification(NotificationType.ERROR, message.toString()));
            model.addAttribute(ATTRIBUTE_CONTRACT, contract);
            model.addAttribute(ATTRIBUTE_LEGAL_ENTITIES, legalEntityService.getAll());
            return "contractEdit";
        }
        contractService.save(contract);
        redirectAttributes.addFlashAttribute(ATTRIBUTE_NOTIFICATION, new Notification(NotificationType.SUCCESS, "Договор успешно создан."));
        return "redirect:/contract";
    }

    @GetMapping("/cancel")
    public String cancel(HttpSession session) {
        session.removeAttribute(ATTRIBUTE_CONTRACT);
        return "redirect:/contract";
    }
}
