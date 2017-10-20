package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.Contract;
import ru.atc.bclient.model.entity.LegalEntity;

import javax.persistence.OrderBy;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    @OrderBy("contract_open_date")
    List<Contract> getAllByIssuerIn(Collection<LegalEntity> legalEntities);
}
