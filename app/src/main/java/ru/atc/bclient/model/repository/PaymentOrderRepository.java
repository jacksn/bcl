package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer> {
    List<PaymentOrder> getAllByDateBetweenAndSenderInOrderByDateAscIdAsc(
            LocalDate startDate,
            LocalDate endDate,
            Collection<LegalEntity> legalEntities);

    PaymentOrder getBySenderInAndId(Collection<LegalEntity> legalEntities, Integer id);

    @Query(value = "SELECT payment_order_num " +
            "FROM fct_payment_order " +
            "WHERE sender_legal_entity_id = ?1 " +
            "ORDER BY payment_order_num DESC " +
            "LIMIT 1",
            nativeQuery = true)
    Integer getLastNumber(Integer legalEntityId);
}
