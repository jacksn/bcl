package ru.atc.bclient;

import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.model.entity.PaymentOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.atc.bclient.AccountTestData.ACCOUNT_1;
import static ru.atc.bclient.AccountTestData.ACCOUNT_3;
import static ru.atc.bclient.AccountTestData.ACCOUNT_5;
import static ru.atc.bclient.AccountTestData.ACCOUNT_7;
import static ru.atc.bclient.ContractTestData.*;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_1;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_2;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_3;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_4;

public final class PaymentOrderTestData {

    public static final PaymentOrder PAYMENT_ORDER_1 = new PaymentOrder(100, 1000, LocalDate.parse("2017-08-07"),
            LEGAL_ENTITY_1, ACCOUNT_1, LEGAL_ENTITY_3, ACCOUNT_5, CONTRACT_3,
            "643", new BigDecimal("1200.00"),
            "Оплата услуги Холодное водостабжение за июль 2017", "05",
            PaymentOrderStatus.of(5), null);

    public static final PaymentOrder PAYMENT_ORDER_2 = new PaymentOrder(101, 200, LocalDate.parse("2017-08-07"),
            LEGAL_ENTITY_2, ACCOUNT_3, LEGAL_ENTITY_3, ACCOUNT_5, CONTRACT_4,
            "643", new BigDecimal("800.00"),
            "Оплата услуги Холодное водостабжение за июль 2017", "05",
            PaymentOrderStatus.of(5), null);

    public static final PaymentOrder PAYMENT_ORDER_3 = new PaymentOrder(102, 1001, LocalDate.parse("2017-08-09"),
            LEGAL_ENTITY_1, ACCOUNT_1, LEGAL_ENTITY_2, ACCOUNT_3, CONTRACT_1,
            "643", new BigDecimal("2000.00"),
            "Оплата услуги Теплоснабжение за июль 2017", "05",
            PaymentOrderStatus.of(5), null);

    public static final PaymentOrder PAYMENT_ORDER_4 = new PaymentOrder(103, 400, LocalDate.parse("2017-08-09"),
            LEGAL_ENTITY_4, ACCOUNT_7, LEGAL_ENTITY_1, ACCOUNT_1, CONTRACT_7,
            "643", new BigDecimal("1500.00"),
            "Оплата поставки товара", "05",
            PaymentOrderStatus.of(5), null);

    public static final PaymentOrder PAYMENT_ORDER_5 = new PaymentOrder(104, 100, LocalDate.parse("2017-08-09"),
            LEGAL_ENTITY_3, ACCOUNT_5, LEGAL_ENTITY_4, ACCOUNT_7, CONTRACT_9,
            "643", new BigDecimal("700.00"),
            "Оплата поставки товара", "05", PaymentOrderStatus.of(5), null);

    public static final List<PaymentOrder> PAYMENT_ORDERS = Arrays.asList(PAYMENT_ORDER_1, PAYMENT_ORDER_2,
            PAYMENT_ORDER_3, PAYMENT_ORDER_4, PAYMENT_ORDER_5);

    public static final List<PaymentOrder> PAYMENT_ORDERS_USER_1 = Arrays.asList(PAYMENT_ORDER_3, PAYMENT_ORDER_1);

    private PaymentOrderTestData() {
    }
}
