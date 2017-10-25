package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.entity.PaymentOrder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.PaymentOrderTestData.PAYMENT_ORDERS_USER_1;
import static ru.atc.bclient.PaymentOrderTestData.PAYMENT_ORDER_1;
import static ru.atc.bclient.PaymentOrderTestData.PAYMENT_ORDER_3;
import static ru.atc.bclient.UserTestData.USER_1;

public class PaymentOrderRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private PaymentOrderRepository repository;

    @Test
    public void findAllByLegalEntity() {
        List<PaymentOrder> paymentOrders = repository.getAllByDateBetweenAndSenderInOrderByDateAscIdAsc(
                PAYMENT_ORDER_1.getDate(),
                PAYMENT_ORDER_3.getDate(),
                USER_1.getLegalEntities());
        assertEquals(PAYMENT_ORDERS_USER_1, paymentOrders);
    }
}
