package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.LegalEntity;

import java.util.List;

public interface LegalEntityService {
    LegalEntity getById(int legalEntityId);

    List<LegalEntity> getAll();
}
