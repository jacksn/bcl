package ru.atc.bclient.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.atc.bclient.model.entity.Bank;

@Getter
@Setter
@ToString(callSuper = true)
public class BankDto extends BaseDto {
    private String name;
    private String inn;
    private String kpp;
    private String bic;
    private String corrAccount;

    public BankDto(Integer id, String name, String inn, String kpp, String bic, String corrAccount) {
        super(id);
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.bic = bic;
        this.corrAccount = corrAccount;
    }

    public static BankDto ofBank(Bank bank) {
        return new BankDto(bank.getId(),
                bank.getName(),
                bank.getInn(),
                bank.getKpp(),
                bank.getBic(),
                bank.getCorrAccount());
    }
}
