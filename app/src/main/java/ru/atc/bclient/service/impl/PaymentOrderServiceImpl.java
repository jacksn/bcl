package ru.atc.bclient.service.impl;

import org.springframework.stereotype.Service;
import ru.atc.bclient.model.entity.Account;
import ru.atc.bclient.model.entity.LegalEntity;
import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.model.entity.PaymentOrderStatus;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.service.PaymentOrderProcessor;
import ru.atc.bclient.service.PaymentOrderService;

import java.time.LocalDate;
import java.util.*;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {
    private PaymentOrderRepository repository;
    private PaymentOrderProcessor processor;

    public PaymentOrderServiceImpl(PaymentOrderRepository repository, PaymentOrderProcessor processor) {
        this.repository = repository;
        this.processor = processor;
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
        for (PaymentOrder paymentOrder : repository.getAllByDateBetweenAndSenderInOrderByDateAscIdAsc(startDate, endDate, senders)) {
            groupedPaymentOrders.get(paymentOrder.getSender()).get(paymentOrder.getSenderAccount()).add(paymentOrder);
        }
        return groupedPaymentOrders;
    }

    @Override
    public PaymentOrder getBySendersAndId(Set<LegalEntity> legalEntities, Integer id) {
        return repository.getBySenderInAndId(legalEntities, id);
    }

    @Override
    public Integer getNewNumber(LegalEntity legalEntity) {
        return repository.getLastNumber(legalEntity.getId()) + 1;
    }

    @Override
    public PaymentOrder save(PaymentOrder paymentOrder) {
        return repository.save(paymentOrder);
    }

    @Override
    public PaymentOrder get(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public void cancel(PaymentOrder paymentOrder) {
        if (paymentOrder == null) {
            throw new IllegalArgumentException("Платежное поручение не найдено.");
        } else if (!(paymentOrder.getStatus().equals(PaymentOrderStatus.NEW)
                || paymentOrder.getStatus().equals(PaymentOrderStatus.ACCEPTED))) {
            throw new IllegalArgumentException("Отмена платежного поручения запрещена.");
        } else if (processor.isProcessingInProgress()) {
            throw new IllegalStateException("Отмена платежного поручения запрещена, идет обработка платежных поручений.");
        }
        paymentOrder.setStatus(PaymentOrderStatus.CANCELLED);
        repository.save(paymentOrder);
    }
}
