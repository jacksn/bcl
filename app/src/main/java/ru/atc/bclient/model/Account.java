package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "dim_account")
@AttributeOverride(name = "id", column = @Column(name = "account_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_account_id")
public class Account extends BaseEntity {
    @Column(name = "account_name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "account_num")
    @NotNull
    @Size(max = 100)
    private String number;

    @OneToOne
    @JoinColumn(name = "legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity legalEntity;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(name = "bank_id", referencedColumnName = "bank_id")
    private Bank bank;

    @Column(name = "currency_code")
    @NotNull
    @Size(max = 10)
    private String currencyCode;

    @Column(name = "account_status_id")
    @NotNull
    @Enumerated
    private AccountStatus status;

    public Account(Integer id, String name, String number, LegalEntity legalEntity, Bank bank, String currencyCode, AccountStatus status) {
        this(name, number, legalEntity, bank, currencyCode, status);
        setId(id);
    }
}
