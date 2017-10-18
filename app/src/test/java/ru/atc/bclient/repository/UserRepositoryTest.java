package ru.atc.bclient.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.atc.bclient.model.Account;
import ru.atc.bclient.model.LegalEntity;
import ru.atc.bclient.model.User;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static ru.atc.bclient.LegalEntityTestData.LEGAL_ENTITY_1;
import static ru.atc.bclient.UserTestData.USERS;
import static ru.atc.bclient.UserTestData.USER_1;

public class UserRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    public void findByLogin() {
        assertEquals(USER_1, repository.findByLogin(USER_1.getLogin()));
    }

    @Test
    public void findAll() {
        assertEquals(USERS, repository.findAll());
    }

    @Test
    public void findWithLegalEntitiesById() {
        User user = repository.findWithLegalEntitiesById(USER_1.getId());
        assertEquals(USER_1, user);
        assertEquals(USER_1.getLegalEntities(), user.getLegalEntities());
    }

    @Test
    public void findWithLegalEntitiesAndAccountsById() {
        User user = repository.findWithLegalEntitiesAndAccountsById(USER_1.getId());
        assertEquals(USER_1, user);
        assertEquals(USER_1.getLegalEntities(), user.getLegalEntities());

        Iterator<LegalEntity> legalEntityIterator = user.getLegalEntities().iterator();
        Set<Account> accounts = legalEntityIterator.next().getAccounts();
        assertEquals(LEGAL_ENTITY_1.getAccounts(), accounts);
    }
}