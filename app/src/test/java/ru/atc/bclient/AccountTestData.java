package ru.atc.bclient;

import ru.atc.bclient.model.Account;
import ru.atc.bclient.model.AccountStatus;

import java.util.Arrays;
import java.util.List;

import static ru.atc.bclient.BankTestData.BANK_1;
import static ru.atc.bclient.BankTestData.BANK_2;
import static ru.atc.bclient.BankTestData.BANK_3;
import static ru.atc.bclient.BankTestData.BANK_5;
import static ru.atc.bclient.LegalEntityTestData.*;

public final class AccountTestData {
    public static final Account ACCOUNT_1 = new Account(1, "Расчётный счёт", "40702810145460034560",
            LEGAL_ENTITY_1, BANK_1, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_2 = new Account(2, "Расчётный счёт", "40702810045500058455",
            LEGAL_ENTITY_1, BANK_1, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_3 = new Account(3, "Расчётный счёт", "40702810145410054511",
            LEGAL_ENTITY_2, BANK_1, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_4 = new Account(4, "Расчётный счёт", "40702810215040001615",
            LEGAL_ENTITY_2, BANK_2, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_5 = new Account(5, "Расчётный счёт", "40702810345410041545",
            LEGAL_ENTITY_3, BANK_1, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_6 = new Account(6, "Расчётный счёт", "40702810432140002151",
            LEGAL_ENTITY_3, BANK_3, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_7 = new Account(7, "Расчётный счёт", "40702810545580000151",
            LEGAL_ENTITY_4, BANK_1, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_8 = new Account(8, "Расчётный счёт", "40702810615040001551",
            LEGAL_ENTITY_4, BANK_2, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_9 = new Account(9, "Расчётный счёт", "40702810732070001515",
            LEGAL_ENTITY_4, BANK_3, "643", AccountStatus.of(1));
    public static final Account ACCOUNT_10 = new Account(10, "Расчётный счёт", "40702840432140018425",
            LEGAL_ENTITY_3, BANK_3, "840", AccountStatus.of(1));
    public static final Account ACCOUNT_11 = new Account(11, "Расчётный счёт", "40101810045250010041",
            LEGAL_ENTITY_5, BANK_5, "643", AccountStatus.of(1));

    public static final List<Account> ACCOUNTS = Arrays.asList(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3, ACCOUNT_4, ACCOUNT_5,
            ACCOUNT_6, ACCOUNT_7, ACCOUNT_8, ACCOUNT_9, ACCOUNT_10, ACCOUNT_11);

    private AccountTestData() {
    }
}
