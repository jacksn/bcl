package ru.atc.bclient.model.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.AccountBalanceTestData.ACCOUNT_BALANCES_ALL;

public class AccountBalanceRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private AccountBalanceRepository repository;

    @Test
    public void findAll() {
        assertEquals(ACCOUNT_BALANCES_ALL, repository.findAll());
    }
}
