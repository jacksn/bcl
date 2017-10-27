package ru.atc.bclient.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "dim_contract")
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_contract_id", allocationSize = 1)
public class Contract extends BaseEntity {
    @Column(name = "contract_name")
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Column(name = "contract_num")
    @NotNull
    @Size(min = 1, max = 20)
    private String number;

    @Column(name = "contract_open_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate openDate;

    @Column(name = "contract_close_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate closeDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "issuer_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity issuer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "signer_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity signer;

    @Column(name = "currency_code")
    @NotNull
    @Size(min = 1, max = 10)
    private String currencyCode;

    public Contract(Integer id, String name, String number, LocalDate openDate, LocalDate closeDate, LegalEntity issuer,
                    LegalEntity signer, String currencyCode) {
        this(name, number, openDate, closeDate, issuer, signer, currencyCode);
        setId(id);
    }
}
