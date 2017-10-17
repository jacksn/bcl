package ru.atc.bclient.model;

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
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name) &&
                Objects.equals(inn, bank.inn) &&
                Objects.equals(kpp, bank.kpp) &&
                Objects.equals(bic, bank.bic) &&
                Objects.equals(corrAccount, bank.corrAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, inn, kpp, bic, corrAccount);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", bic='" + bic + '\'' +
                ", corrAccount='" + corrAccount + '\'' +
                '}';
    }
}
