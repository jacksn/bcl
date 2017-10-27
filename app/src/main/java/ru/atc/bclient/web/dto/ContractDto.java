package ru.atc.bclient.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.atc.bclient.model.entity.Contract;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
public class ContractDto extends BaseDto {
    private String name;
    private String number;
    private LocalDate openDate;
    private LocalDate closeDate;
    private LegalEntityDto issuer;
    private LegalEntityDto signer;
    private String currencyCode;

    public ContractDto(Integer id, String name, String number, LocalDate openDate, LocalDate closeDate, LegalEntityDto issuer, LegalEntityDto signer, String currencyCode) {
        super(id);
        this.name = name;
        this.number = number;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.issuer = issuer;
        this.signer = signer;
        this.currencyCode = currencyCode;
    }

    public static ContractDto ofContract(Contract contract) {
        return new ContractDto(contract.getId(),
                contract.getName(),
                contract.getNumber(),
                contract.getOpenDate(),
                contract.getCloseDate(),
                LegalEntityDto.ofLegalEntity(contract.getIssuer()),
                LegalEntityDto.ofLegalEntity(contract.getSigner()),
                contract.getCurrencyCode());
    }
}
