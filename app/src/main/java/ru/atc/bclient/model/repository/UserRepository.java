package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.User;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
