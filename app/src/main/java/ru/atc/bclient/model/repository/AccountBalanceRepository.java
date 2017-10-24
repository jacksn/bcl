package ru.atc.bclient.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.AccountBalance;

@Transactional
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Integer> {
    AccountBalance getFirstByAccountIdOrderByDateDesc(Integer accountId);
}
