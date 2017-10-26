package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.atc.bclient.model.entity.AccountBalance;

public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer> {
    AccountBalance getFirstByAccountIdOrderByDateDesc(Integer accountId);
}
