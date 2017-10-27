package ru.atc.bclient.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.AccountStatus;

@Getter
@Setter
@ToString(callSuper = true)
public class AccountDto extends BaseDto {
    private String name;
    private String number;
    private BankDto bank;
    private String currencyCode;
    private AccountStatus status;

    public AccountDto(Integer id, String name, String number, BankDto bank, String currencyCode, AccountStatus status) {
        super(id);
        this.name = name;
        this.number = number;
        this.bank = bank;
        this.currencyCode = currencyCode;
        this.status = status;
    }

    public static AccountDto ofAccount(Account account) {
        return new AccountDto(account.getId(),
                account.getName(),
                account.getNumber(),
                BankDto.ofBank(account.getBank()),
                account.getCurrencyCode(),
                account.getStatus());
    }
}
