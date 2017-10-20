package ru.atc.bclient.service;

import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface PaymentOrderService {
    List<PaymentOrder> getAllBySenders(Collection<LegalEntity> senders);

    Map<LegalEntity, Map<Account, List<PaymentOrder>>> getAllBySendersGroupByLegalEntityAndAccount(Collection<LegalEntity> senders);
}
