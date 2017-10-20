package ru.atc.bclient.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.web.security.AuthorizedUser;

@Controller
@RequestMapping("/contract")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public String getContracts(Model model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute("contractMap",
                contractService.getAllByIssuersGroupByLegalEntity(authorizedUser.getLegalEntities()));
        return "contracts";
    }
}
