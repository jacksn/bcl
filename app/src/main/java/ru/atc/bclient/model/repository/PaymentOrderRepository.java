package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;

import javax.persistence.OrderBy;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer> {
    @OrderBy("payment_order_date")
    List<PaymentOrder> getAllByDateBetweenAndSenderIn(
            LocalDate startDate,
            LocalDate endDate,
            Collection<LegalEntity> legalEntities);

    PaymentOrder getBySenderInAndId(Collection<LegalEntity> legalEntities, Integer id);
}
