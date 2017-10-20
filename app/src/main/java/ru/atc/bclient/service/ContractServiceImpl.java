package ru.atc.bclient.service;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.repository.ContractRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {
    private ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public List<Contract> getAllByIssuers(Collection<LegalEntity> issuers) {
        return contractRepository.getAllByIssuerIn(issuers);
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

}
