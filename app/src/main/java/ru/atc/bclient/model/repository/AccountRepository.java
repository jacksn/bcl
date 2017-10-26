package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
