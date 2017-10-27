package ru.atc.bclient.web.controller.ajax;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.web.dto.ContractDto;
import ru.atc.bclient.web.security.AuthorizedUser;

@Controller
@RequestMapping(value = "/ajax/contracts", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class ContractRestController {
    private ContractService service;

    public ContractRestController(ContractService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ContractDto get(@PathVariable("id") Integer id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Contract contract = service.get(id);
        if (authorizedUser.getLegalEntities().contains(contract.getIssuer())) {
            return ContractDto.ofContract(contract);
        } else {
            return null;
        }
    }
}
