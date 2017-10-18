package ru.atc.bclient;

import ru.atc.bclient.model.LegalEntity;
import ru.atc.bclient.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_1;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_2;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_3;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_4;

public final class UserTestData {
    public static final User USER_1 = new User(1, "aivanov", "Иванов Александр Дмитриевич", "ivanov");
    public static final User USER_2 = new User(2, "apetrov", "Петров Алексей Владимирович", "petrov");
    public static final User USER_3 = new User(3, "asidorov", "Сидоров Андрей Григорьевич", "sidorov");

    static {
        Set<LegalEntity> legalEntities = new HashSet<>();
        legalEntities.add(LEGAL_ENTITY_1);
        USER_1.setLegalEntities(legalEntities);

        legalEntities = new HashSet<>();
        legalEntities.add(LEGAL_ENTITY_2);
        legalEntities.add(LEGAL_ENTITY_3);
        USER_2.setLegalEntities(legalEntities);

        legalEntities = new HashSet<>();
        legalEntities.add(LEGAL_ENTITY_4);
        USER_2.setLegalEntities(legalEntities);
    }

    public static final List<User> USERS = Arrays.asList(USER_1, USER_2, USER_3);

    private UserTestData() {
    }
}
