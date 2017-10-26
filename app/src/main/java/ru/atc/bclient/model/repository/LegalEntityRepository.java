package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.LegalEntity;

public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {
}
