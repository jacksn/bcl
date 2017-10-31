package ru.atc.bclient.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.*;
import ru.atc.bclient.model.repository.AccountBalanceRepository;
import ru.atc.bclient.model.repository.OperationRepository;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.service.PaymentOrderProcessor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentOrderProcessorImpl implements PaymentOrderProcessor {
    private PaymentOrderRepository paymentOrderRepository;
    private AccountBalanceRepository accountBalanceRepository;
    private OperationRepository operationRepository;

    private Boolean processingInProgress;

    public PaymentOrderProcessorImpl(PaymentOrderRepository paymentOrderRepository,
                                     AccountBalanceRepository accountBalanceRepository,
                                     OperationRepository operationRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.accountBalanceRepository = accountBalanceRepository;
        this.operationRepository = operationRepository;
        this.processingInProgress = false;
    }

    @Override
    public synchronized Boolean isProcessingInProgress() {
        return processingInProgress;
    }

    private synchronized void setProcessingInProgress(Boolean processingInProgress) {
        this.processingInProgress = processingInProgress;
    }

    @Scheduled(cron = "${application.processing.schedule}")
    @Override
    @Transactional
    public void process() {
        if (!isProcessingInProgress()) {
            setProcessingInProgress(true);
            log.info("Processing start");
            try {
                LocalDate currentDate = LocalDate.now();
                List<PaymentOrder> paymentOrdersToProcess =
                        paymentOrderRepository.getAllByDateBetweenOrderByPriorityCodeAscDateAscIdAsc(currentDate, currentDate).stream()
                                .filter(paymentOrder -> paymentOrder.getStatus().equals(PaymentOrderStatus.ACCEPTED))
                                .collect(Collectors.toCollection(LinkedList::new));

                Map<Integer, AccountBalance> accountBalanceMap = new HashMap<>();
                List<Operation> operations = new LinkedList<>();

                int processedPaymentOrders;

                do {
                    Iterator<PaymentOrder> iterator = paymentOrdersToProcess.iterator();
                    processedPaymentOrders = 0;
                    while (iterator.hasNext()) {
                        PaymentOrder paymentOrder = iterator.next();
                        paymentOrder.setStatus(PaymentOrderStatus.IN_PROGRESS);

                        if (isPaymentOrderValid(paymentOrder)) {
                            AccountBalance senderAccountBalance =
                                    getAccountBalance(currentDate, paymentOrder.getSenderAccount().getId(), accountBalanceMap);
                            if (senderAccountBalance.getAmount().compareTo(paymentOrder.getAmount()) > 0) {
                                AccountBalance recipientAccountBalance =
                                        getAccountBalance(currentDate, paymentOrder.getRecipientAccount().getId(), accountBalanceMap);

                                operations.add(getOperation(paymentOrder));

                                BigDecimal amount = paymentOrder.getAmount();

                                senderAccountBalance.setAmount(senderAccountBalance.getAmount().add(amount.negate()));
                                recipientAccountBalance.setAmount(recipientAccountBalance.getAmount().add(amount));

                                paymentOrder.setStatus(PaymentOrderStatus.EXECUTED);
                                iterator.remove();
                                processedPaymentOrders++;
                            }
                        } else {
                            iterator.remove();
                            processedPaymentOrders++;
                            paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                        }
                        paymentOrderRepository.save(paymentOrder);
                    }
                } while (paymentOrdersToProcess.size() > 0 && processedPaymentOrders > 0);

                for (PaymentOrder paymentOrder : paymentOrdersToProcess) {
                    paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                    paymentOrder.setRejectReason("На счете отправителя недостаточно средств.");
                    paymentOrderRepository.save(paymentOrder);
                }
                operationRepository.save(operations);
                accountBalanceRepository.save(accountBalanceMap.values());
            } catch (Exception e) {
                log.error("Error processing payment orders", e);
                throw e;
            } finally {
                log.info("Processing end");
                setProcessingInProgress(false);
            }
        }
    }

    private boolean isPaymentOrderValid(PaymentOrder paymentOrder) {
        if (!paymentOrder.getSenderAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            paymentOrder.setRejectReason("Счет отправителя не активен.");
            return false;
        } else if (!paymentOrder.getRecipientAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            paymentOrder.setRejectReason("Счет получателя не активен.");
            return false;
        }
        return true;
    }

    private AccountBalance getAccountBalance(LocalDate date, Integer accountId, Map<Integer, AccountBalance> accountBalanceMap) {

        AccountBalance accountBalance = accountBalanceMap.get(accountId);

        if (accountBalance == null) {
            accountBalance = accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(accountId);
            if (!date.isEqual(accountBalance.getDate())) {
                accountBalance = new AccountBalance(date, accountBalance.getAmount(), accountBalance.getAccount());
            }
            accountBalanceMap.put(accountId, accountBalance);
        }

        return accountBalance;
    }

    private Operation getOperation(PaymentOrder paymentOrder) {
        return new Operation(
                paymentOrder.getDate(),
                paymentOrder.getAmount(),
                paymentOrder.getSenderAccount(),
                paymentOrder.getRecipientAccount(),
                paymentOrder.getReason());
    }
}
