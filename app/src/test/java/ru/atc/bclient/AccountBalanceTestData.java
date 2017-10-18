package ru.atc.bclient;

import ru.atc.bclient.model.AccountBalance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.atc.bclient.AccountTestData.*;

public final class AccountBalanceTestData {
    public static final AccountBalance ACCOUNT_BALANCE_1 = new AccountBalance(100, LocalDate.parse("2017-08-01"),
            new BigDecimal("50000.00"), ACCOUNT_1);
    public static final AccountBalance ACCOUNT_BALANCE_2 = new AccountBalance(101, LocalDate.parse("2017-08-01"),
            new BigDecimal("50000.00"), ACCOUNT_2);
    public static final AccountBalance ACCOUNT_BALANCE_3 = new AccountBalance(102, LocalDate.parse("2017-08-01"),
            new BigDecimal("50000.00"), ACCOUNT_3);
    public static final AccountBalance ACCOUNT_BALANCE_4 = new AccountBalance(103, LocalDate.parse("2017-08-01"),
            new BigDecimal("100000.00"), ACCOUNT_4);
    public static final AccountBalance ACCOUNT_BALANCE_5 = new AccountBalance(104, LocalDate.parse("2017-08-01"),
            new BigDecimal("100000.00"), ACCOUNT_5);
    public static final AccountBalance ACCOUNT_BALANCE_6 = new AccountBalance(105, LocalDate.parse("2017-08-01"),
            new BigDecimal("0.00"), ACCOUNT_6);
    public static final AccountBalance ACCOUNT_BALANCE_7 = new AccountBalance(106, LocalDate.parse("2017-08-01"),
            new BigDecimal("60000.00"), ACCOUNT_7);
    public static final AccountBalance ACCOUNT_BALANCE_8 = new AccountBalance(107, LocalDate.parse("2017-08-01"),
            new BigDecimal("70000.00"), ACCOUNT_9);
    public static final AccountBalance ACCOUNT_BALANCE_9 = new AccountBalance(108, LocalDate.parse("2017-08-01"),
            new BigDecimal("500.00"), ACCOUNT_10);

    public static final AccountBalance ACCOUNT_BALANCE_10 = new AccountBalance(109, LocalDate.parse("2017-08-07"),
            new BigDecimal("48800.00"), ACCOUNT_1);
    public static final AccountBalance ACCOUNT_BALANCE_11 = new AccountBalance(110, LocalDate.parse("2017-08-07"),
            new BigDecimal("49200.00"), ACCOUNT_3);
    public static final AccountBalance ACCOUNT_BALANCE_12 = new AccountBalance(111, LocalDate.parse("2017-08-07"),
            new BigDecimal("102000.00"), ACCOUNT_5);
    public static final AccountBalance ACCOUNT_BALANCE_13 = new AccountBalance(112, LocalDate.parse("2017-08-09"),
            new BigDecimal("48300.00"), ACCOUNT_1);
    public static final AccountBalance ACCOUNT_BALANCE_14 = new AccountBalance(113, LocalDate.parse("2017-08-09"),
            new BigDecimal("51200.00"), ACCOUNT_3);
    public static final AccountBalance ACCOUNT_BALANCE_15 = new AccountBalance(114, LocalDate.parse("2017-08-09"),
            new BigDecimal("101300.00"), ACCOUNT_5);
    public static final AccountBalance ACCOUNT_BALANCE_16 = new AccountBalance(115, LocalDate.parse("2017-08-09"),
            new BigDecimal("59200.00"), ACCOUNT_7);

    public static final List<AccountBalance> ACCOUNT_BALANCES_ALL = Arrays.asList(
            ACCOUNT_BALANCE_1, ACCOUNT_BALANCE_2, ACCOUNT_BALANCE_3, ACCOUNT_BALANCE_4, ACCOUNT_BALANCE_5,
            ACCOUNT_BALANCE_6, ACCOUNT_BALANCE_7, ACCOUNT_BALANCE_8, ACCOUNT_BALANCE_9, ACCOUNT_BALANCE_10,
            ACCOUNT_BALANCE_11, ACCOUNT_BALANCE_12, ACCOUNT_BALANCE_13, ACCOUNT_BALANCE_14, ACCOUNT_BALANCE_15,
            ACCOUNT_BALANCE_16);

    public static final List<AccountBalance> ACCOUNT_BALANCES_01 = Arrays.asList(
            ACCOUNT_BALANCE_1, ACCOUNT_BALANCE_2, ACCOUNT_BALANCE_3, ACCOUNT_BALANCE_4, ACCOUNT_BALANCE_5,
            ACCOUNT_BALANCE_6, ACCOUNT_BALANCE_7, ACCOUNT_BALANCE_8, ACCOUNT_BALANCE_9);

    public static final List<AccountBalance> ACCOUNT_BALANCES_07 = Arrays.asList(
            ACCOUNT_BALANCE_10, ACCOUNT_BALANCE_11, ACCOUNT_BALANCE_12, ACCOUNT_BALANCE_13);

    public static final List<AccountBalance> ACCOUNT_BALANCES_09 = Arrays.asList(
            ACCOUNT_BALANCE_14, ACCOUNT_BALANCE_15, ACCOUNT_BALANCE_16);

    private AccountBalanceTestData() {
    }
}
