package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;

import java.util.Collection;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> getAllByIssuerInOrderByOpenDateAsc(Collection<LegalEntity> legalEntities);
}
