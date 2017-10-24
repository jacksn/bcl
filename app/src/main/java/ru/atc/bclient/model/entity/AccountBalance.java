package ru.atc.bclient.model.entity;

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
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "fct_account_balance")
@AttributeOverride(name = "id", column = @Column(name = "account_balance_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_account_balance_id", allocationSize = 1)
public class AccountBalance extends BaseEntity {
    @Column(name = "account_balance_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate date;

    @Column(name = "account_balance_amt")
    @NotNull
    private BigDecimal amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    public AccountBalance(Integer id, LocalDate date, BigDecimal amount, Account account) {
        this(date, amount, account);
        setId(id);
    }
}
