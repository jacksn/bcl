package ru.atc.bclient.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.LegalEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITIES;
import static ru.atc.bclient.UserTestData.USER_1;

public class LegalEntityRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private LegalEntityRepository repository;

    @Test
    public void findAll() {
        List<LegalEntity> legalEntities = repository.findAll();
        assertEquals(LEGAL_ENTITIES, legalEntities);
    }

    @Test
    public void getByUserId() {
        List<LegalEntity> legalEntities = repository.getByUserId(USER_1.getId());
        legalEntities.forEach(System.out::println);
    }

}
