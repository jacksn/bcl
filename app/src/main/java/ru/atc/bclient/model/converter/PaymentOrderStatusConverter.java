package ru.atc.bclient.model.converter;

import ru.atc.bclient.model.entity.PaymentOrderStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentOrderStatusConverter implements AttributeConverter<PaymentOrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PaymentOrderStatus paymentOrderStatus) {
        return paymentOrderStatus.getId();
    }

    @Override
    public PaymentOrderStatus convertToEntityAttribute(Integer id) {
        return PaymentOrderStatus.of(id);
    }
}
