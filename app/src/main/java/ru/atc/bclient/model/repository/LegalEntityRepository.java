package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.LegalEntity;

import javax.persistence.OrderBy;
import java.util.List;

@Transactional
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {
    @Override
    @OrderBy("legal_entity_full_name")
    List<LegalEntity> findAll();
}
