package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.Account;

@Transactional
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
}
