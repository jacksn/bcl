package ru.atc.bclient.web.controller;

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
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.service.LegalEntityService;
import ru.atc.bclient.web.dto.Notification;
import ru.atc.bclient.web.dto.NotificationType;
import ru.atc.bclient.web.security.AuthorizedUser;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static ru.atc.bclient.web.controller.ContractController.PATH;

@Controller
@RequestMapping(PATH)
public class ContractController extends AbstractController {
    static final String PATH = "/contract";

    private ContractService contractService;
    private LegalEntityService legalEntityService;

    public ContractController(ContractService contractService, LegalEntityService legalEntityService) {
        this.contractService = contractService;
        this.legalEntityService = legalEntityService;
    }

    @GetMapping
    public String getContracts(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute(ATTR_CONTRACTS,
                contractService.getAllByIssuersGroupByLegalEntity(authorizedUser.getLegalEntities()));
        return "contracts";
    }

    @PostMapping
    public String createContract(Model model, RedirectAttributes redirectAttributes,
                                 @RequestParam Integer issuerId,
                                 @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        LegalEntity legalEntity = legalEntityService.get(issuerId);
        if (legalEntity == null || !authorizedUser.getLegalEntities().contains(legalEntity)) {
            redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                    new Notification(NotificationType.ERROR, "Ошибка создания договора"));
            return REDIRECT + PATH;
        }
        LocalDate currentDate = LocalDate.now();
        Contract contract = new Contract();
        contract.setIssuer(legalEntity);
        contract.setOpenDate(currentDate);
        contract.setCloseDate(currentDate.plus(1, ChronoUnit.YEARS).minus(1, ChronoUnit.DAYS));
        model.addAttribute(ATTR_CONTRACT, contract);
        model.addAttribute(ATTR_LEGAL_ENTITIES, legalEntityService.getAll());
        return "contractEdit";
    }

    @PostMapping("save")
    public String saveContract(Model model, RedirectAttributes redirectAttributes,
                               @Valid @ModelAttribute Contract contract, BindingResult result) {
        if (result.hasErrors()) {
            String message = "<strong>При сохранении договора произошли следующие ошибки:</strong>" +
                    getFieldErrorMessages(result.getFieldErrors());
            model.addAttribute(ATTR_NOTIFICATION, new Notification(NotificationType.ERROR, message));
            model.addAttribute(ATTR_CONTRACT, contract);
            model.addAttribute(ATTR_LEGAL_ENTITIES, legalEntityService.getAll());
            return "contractEdit";
        }
        contractService.save(contract);
        redirectAttributes.addFlashAttribute(ATTR_NOTIFICATION,
                new Notification(NotificationType.SUCCESS, "Договор успешно создан."));
        return REDIRECT + PATH;
    }
}
