package ru.atc.bclient.web.controller.ajax;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.atc.bclient.service.LegalEntityService;
import ru.atc.bclient.web.dto.LegalEntityDto;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/ajax/legalEntities", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public class LegalEntityRestController {
    private LegalEntityService service;

    public LegalEntityRestController(LegalEntityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public LegalEntityDto get(@PathVariable("id") Integer id) {
        return LegalEntityDto.ofLegalEntity(service.get(id));
    }

    @GetMapping
    public List<LegalEntityDto> getAll() {
        return service.getAll().stream().map(LegalEntityDto::ofLegalEntity).collect(Collectors.toList());
    }
}
