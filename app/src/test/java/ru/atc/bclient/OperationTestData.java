package ru.atc.bclient;

import ru.atc.bclient.model.Operation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.atc.bclient.AccountTestData.ACCOUNT_1;
import static ru.atc.bclient.AccountTestData.ACCOUNT_3;
import static ru.atc.bclient.AccountTestData.ACCOUNT_5;
import static ru.atc.bclient.AccountTestData.ACCOUNT_7;

public final class OperationTestData {
    public static final Operation OPERATION_1 = new Operation(100, LocalDate.parse("2017-08-07"), new BigDecimal("1200.00"),
            ACCOUNT_1, ACCOUNT_5, "Оплата услуги Холодное водостабжение за июль 2017. ПП № 1000");
    public static final Operation OPERATION_2 = new Operation(101, LocalDate.parse("2017-08-07"), new BigDecimal("800.00"),
            ACCOUNT_3, ACCOUNT_5, "Оплата услуги Холодное водостабжение за июль 2017. ПП № 200");
    public static final Operation OPERATION_3 = new Operation(102, LocalDate.parse("2017-08-09"), new BigDecimal("2000.00"),
            ACCOUNT_1, ACCOUNT_3, "Оплата услуги Теплоснабжение за июль 2017. ПП № 1001");
    public static final Operation OPERATION_4 = new Operation(103, LocalDate.parse("2017-08-09"), new BigDecimal("1500.00"),
            ACCOUNT_7, ACCOUNT_1, "Оплата поставки товара. ПП № 400");
    public static final Operation OPERATION_5 = new Operation(104, LocalDate.parse("2017-08-09"), new BigDecimal("700.00"),
            ACCOUNT_5, ACCOUNT_7, "Оплата поставки товара. ПП № 100");

    public static final List<Operation> OPERATIONS =
            Arrays.asList(OPERATION_1, OPERATION_2, OPERATION_3, OPERATION_4, OPERATION_5);

    private OperationTestData() {
    }
}
