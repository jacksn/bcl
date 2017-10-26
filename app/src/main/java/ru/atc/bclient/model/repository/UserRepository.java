package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByLogin(String login);
}
