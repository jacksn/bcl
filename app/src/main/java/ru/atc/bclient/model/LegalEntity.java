package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "dim_user")
@AttributeOverride(name = "id", column = @Column(name = "legal_entity_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_legal_entity_id")
public class LegalEntity extends BaseEntity {
    @Column(name = "legal_entity_short_name")
    @NotNull
    @Size(max = 100)
    private String shortName;

    @Column(name = "legal_entity_full_name")
    @NotNull
    @Size(max = 300)
    private String fullName;

    @Column(name = "legal_entity_inn")
    @NotNull
    @Size(max = 20)
    private String inn;

    @Column(name = "legal_entity_kpp")
    @NotNull
    @Size(max = 20)
    private String kpp;

    @Column(name = "legal_entity_ogrn")
    @NotNull
    @Size(max = 20)
    private String ogrn;

    @Column(name = "legal_address")
    @NotNull
    @Size(max = 500)
    private String address;

    public LegalEntity(Integer id, String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        this(shortName, fullName, inn, kpp, ogrn, address);
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LegalEntity that = (LegalEntity) o;
        return Objects.equals(shortName, that.shortName) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(inn, that.inn) &&
                Objects.equals(kpp, that.kpp) &&
                Objects.equals(ogrn, that.ogrn) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shortName, fullName, inn, kpp, ogrn, address);
    }

    @Override
    public String toString() {
        return "LegalEntity{" +
                "id='" + getId() + '\'' +
                ", shortName='" + shortName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", ogrn='" + ogrn + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
