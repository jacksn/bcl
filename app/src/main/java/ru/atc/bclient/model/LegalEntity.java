package ru.atc.bclient.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = "accounts")
@Entity
@Table(name = "dim_legal_entity")
@AttributeOverride(name = "id", column = @Column(name = "legal_entity_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_legal_entity_id")
public class LegalEntity extends BaseEntity {
    @NonNull
    @Column(name = "legal_entity_short_name")
    @NotNull
    @Size(max = 100)
    private String shortName;

    @NonNull
    @Column(name = "legal_entity_full_name")
    @NotNull
    @Size(max = 300)
    private String fullName;

    @NonNull
    @Column(name = "legal_entity_inn")
    @NotNull
    @Size(max = 20)
    private String inn;

    @NonNull
    @Column(name = "legal_entity_kpp")
    @NotNull
    @Size(max = 20)
    private String kpp;

    @Column(name = "legal_entity_ogrn")
    @Size(max = 20)
    private String ogrn;

    @Column(name = "legal_address")
    @Size(max = 500)
    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "legal_entity_id")
    private Set<Account> accounts;

    public LegalEntity(Integer id, String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        this(shortName, fullName, inn, kpp, ogrn, address);
        setId(id);
    }

    public LegalEntity(String shortName, String fullName, String inn, String kpp, String ogrn, String address) {
        this.shortName = shortName;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
        this.ogrn = ogrn;
        this.address = address;
    }
}
