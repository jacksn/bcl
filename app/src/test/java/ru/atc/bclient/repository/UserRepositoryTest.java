package ru.atc.bclient.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
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
}