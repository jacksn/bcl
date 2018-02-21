package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.entity.Operation;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.OperationTestData.OPERATIONS;

public class OperationRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private OperationRepository repository;

    @Test
    public void findAll() {
        List<Operation> operations = repository.findAll();
        assertEquals(OPERATIONS, operations);
    }
}
