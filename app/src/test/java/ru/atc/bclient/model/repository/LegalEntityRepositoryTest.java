package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.entity.LegalEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITIES;

public class LegalEntityRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private LegalEntityRepository repository;

    @Test
    public void findAll() {
        List<LegalEntity> legalEntities = repository.findAll();
        assertEquals(LEGAL_ENTITIES, legalEntities);
    }
}
