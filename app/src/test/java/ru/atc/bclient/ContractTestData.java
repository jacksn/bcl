package ru.atc.bclient;

import ru.atc.bclient.model.entity.Contract;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_1;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_2;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_3;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_4;

public final class ContractTestData {

    public static final Contract CONTRACT_1 = new Contract(1, "Договор оказания услуг", "15845",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_2, LEGAL_ENTITY_1, "643");
    public static final Contract CONTRACT_2 = new Contract(2, "Договор оказания услуг", "16854",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_2, LEGAL_ENTITY_3, "643");
    public static final Contract CONTRACT_3 = new Contract(3, "Договор оказания услуг", "842",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_3, LEGAL_ENTITY_1, "643");
    public static final Contract CONTRACT_4 = new Contract(4, "Договор оказания услуг", "843",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_3, LEGAL_ENTITY_2, "643");
    public static final Contract CONTRACT_5 = new Contract(5, "Договор оказания услуг", "872",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_3, LEGAL_ENTITY_4, "643");
    public static final Contract CONTRACT_6 = new Contract(6, "Договор поставки товаров", "85/12",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_1, LEGAL_ENTITY_2, "643");
    public static final Contract CONTRACT_7 = new Contract(7, "Договор поставки товаров", "45/9",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_1, LEGAL_ENTITY_4, "643");
    public static final Contract CONTRACT_8 = new Contract(8, "Договор поставки товаров", "002518",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_4, LEGAL_ENTITY_2, "643");
    public static final Contract CONTRACT_9 = new Contract(9, "Договор поставки товаров", "002521",
            LocalDate.parse("2017-01-01"), LocalDate.parse("2017-12-31"), LEGAL_ENTITY_4, LEGAL_ENTITY_3, "643");


    public static final List<Contract> CONTRACTS = Arrays.asList(CONTRACT_1, CONTRACT_2, CONTRACT_3, CONTRACT_4,
            CONTRACT_5, CONTRACT_6, CONTRACT_7, CONTRACT_8, CONTRACT_9);

    private ContractTestData() {
    }
}
