package ru.atc.bclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fct_account_balance")
@AttributeOverride(name = "id", column = @Column(name = "account_balance_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_account_balance_id")
public class AccountBalance extends BaseEntity {
    @Column(name = "account_balance_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate date;

    @Column(name = "account_balance_amt")
    @NotNull
    private BigDecimal amount;

    @OneToOne(optional = false)
    @NonNull
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    public AccountBalance(Integer id, LocalDate date, BigDecimal amount, Account account) {
        this(date, amount, account);
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountBalance that = (AccountBalance) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date, amount, account);
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
                "id='" + getId() + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                ", account=" + account +
                '}';
    }
}
