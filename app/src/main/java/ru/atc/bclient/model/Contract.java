package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dim_contract")
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_contract_id")
public class Contract extends BaseEntity {
    @Column(name = "contract_name")
    @NotNull
    @Size(max = 100)
    private String name;

    @Column(name = "contract_num")
    @NotNull
    @Size(max = 20)
    private String number;

    @Column(name = "contract_open_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate openDate;

    @Column(name = "contract_close_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate closeDate;

    @OneToOne(optional = false)
    @JoinColumn(name = "issuer_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity issuer;

    @OneToOne(optional = false)
    @JoinColumn(name = "signer_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity signer;

    @Column(name = "currency_code")
    @NotNull
    @Size(max = 10)
    private String currencyCode;

    public Contract(Integer id, String name, String number, LocalDate openDate, LocalDate closeDate, LegalEntity issuer,
                    LegalEntity signer, String currencyCode) {
        this(name, number, openDate, closeDate, issuer, signer, currencyCode);
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Contract contract = (Contract) o;
        return Objects.equals(name, contract.name) &&
                Objects.equals(number, contract.number) &&
                Objects.equals(openDate, contract.openDate) &&
                Objects.equals(closeDate, contract.closeDate) &&
                Objects.equals(issuer, contract.issuer) &&
                Objects.equals(signer, contract.signer) &&
                Objects.equals(currencyCode, contract.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, number, openDate, closeDate, issuer, signer, currencyCode);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", issuer=" + issuer +
                ", signer=" + signer +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
