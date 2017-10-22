package ru.atc.bclient.model.converter;

import ru.atc.bclient.model.entity.AccountStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccountStatusConverter implements AttributeConverter<AccountStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountStatus accountStatus) {
        return accountStatus.getId();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer id) {
        return AccountStatus.of(id);
    }
}
