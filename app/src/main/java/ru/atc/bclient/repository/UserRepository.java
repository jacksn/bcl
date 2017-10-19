package ru.atc.bclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
