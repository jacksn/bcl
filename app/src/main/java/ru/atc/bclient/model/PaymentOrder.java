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
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fct_payment_order")
@AttributeOverride(name = "id", column = @Column(name = "payment_order_id"))
@SequenceGenerator(name = "default_gen", sequenceName = "seq_payment_order_id")
public class PaymentOrder extends BaseEntity {
    @Column(name = "payment_order_num")
    @NotNull
    private int number;

    @Column(name = "payment_order_date", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
    private LocalDate date;

    @OneToOne(optional = false)
    @JoinColumn(name = "sender_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity sender;

    @OneToOne(optional = false)
    @JoinColumn(name = "sender_account_id", referencedColumnName = "account_id")
    private Account senderAccount;

    @OneToOne(optional = false)
    @JoinColumn(name = "recipient_legal_entity_id", referencedColumnName = "legal_entity_id")
    private LegalEntity recipient;

    @OneToOne(optional = false)
    @JoinColumn(name = "recipient_account_id", referencedColumnName = "account_id")
    private Account recipientAccount;

    @OneToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "contract_id")
    private Contract contract;

    @Column(name = "currency_code")
    @NotNull
    @Size(max = 10)
    private String currencyCode;

    @Column(name = "payment_order_amt")
    @NotNull
    private BigDecimal amount;

    @Column(name = "payment_reason")
    @Size(max = 500)
    private String reason;

    @Column(name = "payment_priority_code")
    @Size(max = 2)
    private String priorityCode;

    @Column(name = "payment_order_status_id")
    @NotNull
    @Enumerated
    private PaymentOrderStatus status;

    @Column(name = "reject_reason")
    @Size(max = 500)
    private String rejectReason;

    public PaymentOrder(Integer id, int number, LocalDate date, LegalEntity sender,
                        Account senderAccount, LegalEntity recipient,
                        Account recipientAccount,
                        Contract contract,
                        String currencyCode, BigDecimal amount,
                        String reason, String priorityCode,
                        PaymentOrderStatus status,
                        String rejectReason) {
        this(number, date,sender,senderAccount,recipient,recipientAccount,contract,currencyCode,amount,reason,priorityCode,status,rejectReason);
        setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PaymentOrder that = (PaymentOrder) o;
        return number == that.number &&
                Objects.equals(date, that.date) &&
                Objects.equals(sender, that.sender) &&
                Objects.equals(senderAccount, that.senderAccount) &&
                Objects.equals(recipient, that.recipient) &&
                Objects.equals(recipientAccount, that.recipientAccount) &&
                Objects.equals(contract, that.contract) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(reason, that.reason) &&
                Objects.equals(priorityCode, that.priorityCode) &&
                status == that.status &&
                Objects.equals(rejectReason, that.rejectReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number, date, sender, senderAccount, recipient, recipientAccount, contract, currencyCode, amount, reason, priorityCode, status, rejectReason);
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "id='" + getId() + '\'' +
                ", number=" + number +
                ", date=" + date +
                ", sender=" + sender +
                ", senderAccount=" + senderAccount +
                ", recipient=" + recipient +
                ", recipientAccount=" + recipientAccount +
                ", contract=" + contract +
                ", currencyCode='" + currencyCode + '\'' +
                ", amount=" + amount +
                ", reason='" + reason + '\'' +
                ", priorityCode='" + priorityCode + '\'' +
                ", status=" + status +
                ", rejectReason='" + rejectReason + '\'' +
                '}';
    }
}
