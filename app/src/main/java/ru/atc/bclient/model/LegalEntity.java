package ru.atc.bclient.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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


    public LegalEntity() {
    }

    public LegalEntity(String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        this(null, shortName, fullName, inn, kpp, ogrn, address);
    }

    public LegalEntity(Integer id, String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        super(id);
        this.shortName = shortName;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.address = address;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getOgrn() {
        return ogrn;
    }

    public void setOgrn(String ogrn) {
        this.ogrn = ogrn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
