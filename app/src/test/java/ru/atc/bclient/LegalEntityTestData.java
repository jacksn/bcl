package ru.atc.bclient;

import ru.atc.bclient.model.Account;
import ru.atc.bclient.model.LegalEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.atc.bclient.AccountTestData.*;

public final class LegalEntityTestData {
    public static final LegalEntity LEGAL_ENTITY_1 = new LegalEntity(1, "Ветмедстаб",
            "ООО \"ВетМедСнаб\"",
            "7714698320", "77145841", "5077746887312",
            "109115 Москва, Тверская-Ямская ул., д. 15 с. 2");

    public static final LegalEntity LEGAL_ENTITY_2 = new LegalEntity(2, "Теплоэнергосбыт",
            "ООО \"ТеплоЭнергоСбыт\"",
            "7714485662", "77145118", "5077642887447",
            "108145 Москва, Академика Янгеля ул., д. 245");

    public static final LegalEntity LEGAL_ENTITY_3 = new LegalEntity(3, "Мосводоканал",
            "ООО \"МосВодоканал\"",
            "7714485272", "77146251", "5077751884565",
            "110102 Москва, Ямского поля ул., д. 31");

    public static final LegalEntity LEGAL_ENTITY_4 = new LegalEntity(4, "Экотекстиль",
            "ООО \"ЭкоТекстиль\"",
            "7714752214", "77144232", "5077746655412",
            "121014 Москва, Полежаевская ул., д. 7 с. 5");

    public static final LegalEntity LEGAL_ENTITY_5 = new LegalEntity(5, "Налоговая № 10",
            "Инспекция Федеральной налоговой службы № 10 по г. Москве",
            "7710047253", "772601001", null,
            "115191 Москва, Б.Тульская ул., д. 15");

    public static final List<LegalEntity> LEGAL_ENTITIES = Arrays.asList(LEGAL_ENTITY_1, LEGAL_ENTITY_2,
            LEGAL_ENTITY_3, LEGAL_ENTITY_4, LEGAL_ENTITY_5);

    static {
        Set<Account> accounts = new HashSet<>();

        accounts.add(ACCOUNT_1);
        accounts.add(ACCOUNT_2);
        LEGAL_ENTITY_1.setAccounts(accounts);

        accounts = new HashSet<>();
        accounts.add(ACCOUNT_3);
        accounts.add(ACCOUNT_4);
        LEGAL_ENTITY_2.setAccounts(accounts);

        accounts = new HashSet<>();
        accounts.add(ACCOUNT_5);
        accounts.add(ACCOUNT_6);
        accounts.add(ACCOUNT_10);
        LEGAL_ENTITY_3.setAccounts(accounts);

        accounts = new HashSet<>();
        accounts.add(ACCOUNT_7);
        accounts.add(ACCOUNT_8);
        accounts.add(ACCOUNT_9);
        LEGAL_ENTITY_4.setAccounts(accounts);

        accounts = new HashSet<>();
        accounts.add(ACCOUNT_11);
        LEGAL_ENTITY_5.setAccounts(accounts);
    }

    private LegalEntityTestData() {
    }
}
