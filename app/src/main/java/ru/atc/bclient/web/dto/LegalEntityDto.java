package ru.atc.bclient.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.atc.bclient.model.entity.LegalEntity;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString(callSuper = true, exclude = "accounts")
public class LegalEntityDto extends BaseDto {
    private String shortName;
    private String fullName;
    private String inn;
    private String kpp;
    private String ogrn;
    private String address;

    private Set<AccountDto> accounts;

    public LegalEntityDto(Integer id, String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        super(id);
        this.shortName = shortName;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.address = address;
    }

    public static LegalEntityDto ofLegalEntity(LegalEntity legalEntity) {
        LegalEntityDto legalEntityDto = new LegalEntityDto(legalEntity.getId(),
                legalEntity.getShortName(),
                legalEntity.getFullName(),
                legalEntity.getInn(),
                legalEntity.getKpp(),
                legalEntity.getOgrn(),
                legalEntity.getAddress());

        if (!legalEntity.getAccounts().isEmpty()) {
            legalEntityDto.setAccounts(
                    legalEntity.getAccounts().stream().map(AccountDto::ofAccount).collect(Collectors.toSet())
            );
        }
        return legalEntityDto;
    }
}
