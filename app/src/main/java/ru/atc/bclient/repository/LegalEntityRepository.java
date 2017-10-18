package ru.atc.bclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.LegalEntity;

import java.util.List;

@Transactional
@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Integer> {
    @Query(value = "SELECT * FROM dim_legal_entity l JOIN rel_user_legal_entity ule ON l.legal_entity_id = ule.legal_entity_id WHERE ule.user_id = ?1",
            nativeQuery = true)
    List<LegalEntity> getByUserId(int userId);
}
