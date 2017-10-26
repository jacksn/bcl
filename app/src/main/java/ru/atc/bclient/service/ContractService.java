package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ContractService {
    List<Contract> getAllByIssuers(Collection<LegalEntity> issuers);

    Map<LegalEntity, List<Contract>> getAllByIssuersGroupByLegalEntity(Collection<LegalEntity> issuers);

    Contract save(Contract contract);

    Contract get(Integer id);

    List<Contract> getAllActive(LegalEntity sender, String currencyCode);
}
