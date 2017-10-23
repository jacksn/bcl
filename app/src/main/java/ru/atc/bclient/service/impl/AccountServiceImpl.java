package ru.atc.bclient.service.impl;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.AccountBalance;
import ru.atc.bclient.model.repository.AccountBalanceRepository;
import ru.atc.bclient.model.repository.AccountRepository;

@Service
public class AccountServiceImpl implements ru.atc.bclient.service.AccountService {
    private AccountBalanceRepository accountBalanceRepository;
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountBalanceRepository accountBalanceRepository, AccountRepository accountRepository) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.accountRepository = accountRepository;
    }

    public AccountBalance getBalance(int accountId) {
        return accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(accountId);
    }

    public Account getById(int accountId) {
        return accountRepository.findOne(accountId);
    }
}
