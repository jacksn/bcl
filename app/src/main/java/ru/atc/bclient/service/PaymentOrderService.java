package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PaymentOrderService {
    Map<LegalEntity, Map<Account, List<PaymentOrder>>> getAllBySendersGroupByLegalEntityAndAccount(
            LocalDate startDate,
            LocalDate endDate,
            Collection<LegalEntity> senders);

    PaymentOrder getBySendersAndId(Set<LegalEntity> legalEntities, Integer id);

    Integer getNewNumber(LegalEntity legalEntity);

    PaymentOrder save(PaymentOrder paymentOrder);
}
