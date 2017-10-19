package ru.atc.bclient;

import ru.atc.bclient.model.entity.Bank;

import java.util.Arrays;
import java.util.List;

public final class BankTestData {
    public static final Bank BANK_1 = new Bank(1, "ПАО \"Сбербанк\"",
            "7707083893", "773601001", "044525225", "30101810400000000225");

    public static final Bank BANK_2 = new Bank(2, "АО \"Альфа-банк\"",
            "7728168971", "770801001", "044525593", "30101810200000000593");

    public static final Bank BANK_3 = new Bank(3, "ПАО \"Банк ВТБ\"",
            "7702070139", "783501001", "044525187", "30101810700000000187");

    public static final Bank BANK_5 = new Bank(5, "ГУ Банка России по ЦФО",
            "7702235133", "770245014", "044525000", null);

    public static final List<Bank> BANKS = Arrays.asList(BANK_1, BANK_2, BANK_3, BANK_5);

    private BankTestData() {
    }
}
