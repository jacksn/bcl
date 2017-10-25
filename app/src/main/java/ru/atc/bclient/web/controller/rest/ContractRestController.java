package ru.atc.bclient.web.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.service.ContractService;
import ru.atc.bclient.web.security.AuthorizedUser;

import static ru.atc.bclient.web.controller.rest.ApiVersion.API_VERSION;

@RestController
@RequestMapping(value = ContractRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ContractRestController {
    static final String REST_URL = API_VERSION + "/contract";

    private ContractService service;

    public ContractRestController(ContractService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Contract get(@PathVariable("id") Integer id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        Contract contract = service.get(id);
        if (authorizedUser.getLegalEntities().contains(contract.getIssuer())) {
            return contract;
        } else {
            return null;
        }
    }
}
