package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

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
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "fct_operation")
@AttributeOverride(name = "id", column = @Column(name = "operation_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_operation_id")
public class Operation extends BaseEntity {
    @Column(name = "operation_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate date;

    @Column(name = "operation_amt")
    @NotNull
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "debet_account_id", referencedColumnName = "account_id")
    private Account debetAccount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "credit_account_id", referencedColumnName = "account_id")
    private Account creditAccount;

    @Column(name = "operation_descr")
    @Size(max = 300)
    private String description;

    public Operation(Integer id, LocalDate date, BigDecimal amount, Account debetAccount, Account creditAccount, String description) {
        this(date, amount, debetAccount, creditAccount, description);
        setId(id);
    }
}
