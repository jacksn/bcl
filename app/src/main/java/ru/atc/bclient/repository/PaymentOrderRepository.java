package ru.atc.bclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.Account;
import ru.atc.bclient.model.LegalEntity;
import ru.atc.bclient.model.PaymentOrder;

import javax.persistence.OrderBy;
import java.util.Collection;
import java.util.List;

@Transactional
@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer> {
    @OrderBy("payment_order_date")
    List<PaymentOrder> getAllBySenderIn(Collection<LegalEntity> legalEntities);

    @OrderBy("payment_order_date")
    List<PaymentOrder> getAllBySenderInAndSenderAccount(Collection<LegalEntity> legalEntities, Account senderAccount);
}
