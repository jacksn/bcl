package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.ContractTestData.CONTRACTS;

public class ContractRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private ContractRepository repository;

    @Test
    public void findAll() {
        assertEquals(CONTRACTS, repository.findAll());
    }
}
