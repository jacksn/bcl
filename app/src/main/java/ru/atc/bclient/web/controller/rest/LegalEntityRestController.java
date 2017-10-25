package ru.atc.bclient.web.controller.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.service.LegalEntityService;

import java.util.List;

import static ru.atc.bclient.web.controller.rest.ApiVersion.API_VERSION;
import static ru.atc.bclient.web.controller.rest.LegalEntityRestController.REST_URL;

@RestController
@RequestMapping(value = REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class LegalEntityRestController {
    static final String REST_URL = API_VERSION + "/legalEntity";

    private LegalEntityService service;

    public LegalEntityRestController(LegalEntityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public LegalEntity get(@PathVariable("id") Integer id) {
        return service.get(id);
    }

    @GetMapping
    public List<LegalEntity> getAll() {
        return service.getAll();
    }
}
