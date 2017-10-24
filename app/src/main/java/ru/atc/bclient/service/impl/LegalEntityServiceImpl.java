package ru.atc.bclient.service.impl;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.repository.LegalEntityRepository;
import ru.atc.bclient.service.LegalEntityService;

import java.util.List;

@Service
public class LegalEntityServiceImpl implements LegalEntityService {
    private LegalEntityRepository repository;

    public LegalEntityServiceImpl(LegalEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public LegalEntity getById(Integer legalEntityId) {
        return repository.findOne(legalEntityId);
    }

    @Override
    public List<LegalEntity> getAll() {
        return repository.findAll();
    }
}
