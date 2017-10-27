package ru.atc.bclient.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PaymentOrderFormData implements Serializable {
    private Integer recipientId;
    private Integer recipientAccountId;
    private Integer contractId;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin("0.01")
    private BigDecimal amount;

    @Size(max = 500)
    @SafeHtml
    private String reason;

    @Size(max = 2)
    @SafeHtml
    private String priorityCode;
}
