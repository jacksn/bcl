package ru.atc.bclient.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);

    @EntityGraph("user.legalEntities")
    User findWithLegalEntitiesById(int id);

    @EntityGraph("user.legalEntities.accounts")
    User findWithLegalEntitiesAndAccountsById(int id);
}
