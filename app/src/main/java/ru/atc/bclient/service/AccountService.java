package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.AccountBalance;

public interface AccountService {
    AccountBalance getBalance(Integer accountId);

    Account getById(Integer accountId);
}
