package ru.atc.bclient.service.impl;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.service.PaymentOrderService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {
    private PaymentOrderRepository paymentOrderRepository;

    public PaymentOrderServiceImpl(PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Override
    public Map<LegalEntity, Map<Account, List<PaymentOrder>>> getAllBySendersGroupByLegalEntityAndAccount(
            LocalDate startDate,
            LocalDate endDate,
            Collection<LegalEntity> senders) {
        Map<LegalEntity, Map<Account, List<PaymentOrder>>> groupedPaymentOrders = new HashMap<>();
        for (LegalEntity sender : senders) {
            Map<Account, List<PaymentOrder>> accountMap = new HashMap<>();
            for (Account account : sender.getAccounts()) {
                accountMap.put(account, new LinkedList<>());
            }
            groupedPaymentOrders.put(sender, accountMap);
        }
        for (PaymentOrder paymentOrder : paymentOrderRepository.getAllByDateBetweenAndSenderIn(startDate, endDate, senders)) {
            groupedPaymentOrders.get(paymentOrder.getSender()).get(paymentOrder.getSenderAccount()).add(paymentOrder);
        }
        return groupedPaymentOrders;
    }

    @Override
    public PaymentOrder getBySendersAndId(Set<LegalEntity> legalEntities, Integer id) {
        return paymentOrderRepository.getBySenderInAndId(legalEntities, id);
    }
}
