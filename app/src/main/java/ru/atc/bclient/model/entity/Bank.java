package ru.atc.bclient.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "dim_bank")
@AttributeOverride(name = "id", column = @Column(name = "bank_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_bank_id")
public class Bank extends BaseEntity {
    @Column(name = "bank_name")
    @NotNull
    @Size(max = 300)
    private String name;

    @Column(name = "bank_inn")
    @NotNull
    @Size(max = 50)
    private String inn;

    @Column(name = "bank_kpp")
    @NotNull
    @Size(max = 50)
    private String kpp;

    @Column(name = "bank_bic")
    @NotNull
    @Size(max = 50)
    private String bic;

    @Column(name = "bank_corr_acc")
    @Size(max = 50)
    private String corrAccount;

    public Bank(Integer id, String name, String inn, String kpp, String bic, String corrAccount) {
        this(name, inn, kpp, bic, corrAccount);
        setId(id);
    }
}
