package ru.atc.bclient.web.controller.ajax;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.service.LegalEntityService;

import java.util.List;

@Controller
@RequestMapping(value = "/ajax/legalEntities", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class LegalEntityRestController {
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
