package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.entity.PaymentOrder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.AccountTestData.ACCOUNT_1;
import static ru.atc.bclient.PaymentOrderTestData.PAYMENT_ORDERS;
import static ru.atc.bclient.PaymentOrderTestData.PAYMENT_ORDERS_USER_1;
import static ru.atc.bclient.UserTestData.USER_1;

public class PaymentOrderRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private PaymentOrderRepository repository;

    @Test
    public void findAll() {
        assertEquals(PAYMENT_ORDERS, repository.findAll());
    }

    @Test
    public void findAllByLegalEntity() {
        List<PaymentOrder> paymentOrders = repository.getAllBySenderIn(USER_1.getLegalEntities());
        assertEquals(PAYMENT_ORDERS_USER_1, paymentOrders);
    }

    @Test
    public void findAllByLegalEntityAndAccount() {
        List<PaymentOrder> paymentOrders = repository.getAllBySenderInAndSenderAccount(USER_1.getLegalEntities(), ACCOUNT_1);
        assertEquals(PAYMENT_ORDERS_USER_1, paymentOrders);
    }
}
