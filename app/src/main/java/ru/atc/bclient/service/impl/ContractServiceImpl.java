package ru.atc.bclient.service.impl;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.repository.ContractRepository;
import ru.atc.bclient.service.ContractService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository repository;

    public ContractServiceImpl(ContractRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Contract> getAllByIssuers(Collection<LegalEntity> issuers) {
        return repository.getAllByIssuerInOrderByOpenDateAsc(issuers);
    }

    @Override
    public Map<LegalEntity, List<Contract>> getAllByIssuersGroupByLegalEntity(Collection<LegalEntity> issuers) {
        Map<LegalEntity, List<Contract>> groupedContracts = new HashMap<>();
        for (LegalEntity issuer : issuers) {
            groupedContracts.put(issuer, new LinkedList<>());
        }
        for (Contract contract : getAllByIssuers(issuers)) {
            groupedContracts.get(contract.getIssuer()).add(contract);
        }
        return groupedContracts;
    }

    @Override
    public Contract save(Contract contract) {
        return repository.save(contract);
    }

    @Override
    public Contract get(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public List<Contract> getAllActive(LegalEntity sender, String currencyCode) {
        return getAllByIssuers(Collections.singletonList(sender))
                .stream()
                .filter(contract -> contract.getCurrencyCode().equals(currencyCode)
                        && contract.getCloseDate().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }
}
