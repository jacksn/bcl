package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.LegalEntity;

@Transactional
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {
}
