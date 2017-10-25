package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.LegalEntity;

import java.util.List;

public interface LegalEntityService {
    LegalEntity get(Integer id);

    List<LegalEntity> getAll();
}
