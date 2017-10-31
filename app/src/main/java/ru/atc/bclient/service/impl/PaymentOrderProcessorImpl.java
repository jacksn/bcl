package ru.atc.bclient.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.atc.bclient.model.entity.AccountBalance;
import ru.atc.bclient.model.entity.AccountStatus;
import ru.atc.bclient.model.entity.Operation;
import ru.atc.bclient.model.entity.PaymentOrder;
import ru.atc.bclient.model.entity.PaymentOrderStatus;
import ru.atc.bclient.model.repository.AccountBalanceRepository;
import ru.atc.bclient.model.repository.OperationRepository;
import ru.atc.bclient.model.repository.PaymentOrderRepository;
import ru.atc.bclient.service.PaymentOrderProcessor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

    @Scheduled(cron = "0 0 18 * * MON-FRI")
    @Override
    @Transactional
    public void process() {
        setProcessingInProgress(true);
        log.info("Processing start");
        try {
            LocalDate currentDate = LocalDate.now();
            List<PaymentOrder> paymentOrdersToProcess =
                    paymentOrderRepository.getAllByDateBetweenOrderByPriorityCodeAscDateAscIdAsc(currentDate, currentDate).stream()
                            .filter(paymentOrder -> paymentOrder.getStatus().equals(PaymentOrderStatus.ACCEPTED))
                            .collect(Collectors.toList());

            for (PaymentOrder paymentOrder : paymentOrdersToProcess) {
                paymentOrder.setStatus(PaymentOrderStatus.IN_PROGRESS);
                if (isPaymentOrderValid(paymentOrder)) {
                    createOperation(paymentOrder);

                    BigDecimal amount = paymentOrder.getAmount();
                    updateAccountBalance(currentDate, paymentOrder.getSenderAccount().getId(), amount.negate());
                    updateAccountBalance(currentDate, paymentOrder.getRecipientAccount().getId(), amount);

                    paymentOrder.setStatus(PaymentOrderStatus.EXECUTED);
                } else {
                    paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                }
                paymentOrderRepository.save(paymentOrder);
            }
        } catch (Exception e) {
            log.error("Error processing payment orders", e);
            throw e;
        }
        log.info("Processing end");
        setProcessingInProgress(false);
    }

    private boolean isPaymentOrderValid(PaymentOrder paymentOrder) {
        if (!paymentOrder.getSenderAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            paymentOrder.setRejectReason("Счет отправителя не активен.");
            return false;
        } else if (!paymentOrder.getRecipientAccount().getStatus().equals(AccountStatus.ACTIVE)) {
            paymentOrder.setRejectReason("Счет получателя не активен.");
            return false;
        } else if (paymentOrder.getAmount()
                .compareTo(accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(paymentOrder.getSenderAccount().getId())
                        .getAmount()) > 0) {
            paymentOrder.setRejectReason("На счете отправителя недостаточно средств.");
            return false;
        }
        return true;
    }

    private void updateAccountBalance(LocalDate date, Integer accountId, BigDecimal amount) {
        AccountBalance accountBalance =
                accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(accountId);
        if (!accountBalance.getDate().isEqual(date)) {
            accountBalance = new AccountBalance(date, accountBalance.getAmount(), accountBalance.getAccount());
        }
        accountBalance.setAmount(accountBalance.getAmount().add(amount));
        accountBalanceRepository.save(accountBalance);
    }

    private void createOperation(PaymentOrder paymentOrder) {
        Operation operation = new Operation(
                paymentOrder.getDate(),
                paymentOrder.getAmount(),
                paymentOrder.getSenderAccount(),
                paymentOrder.getRecipientAccount(),
                paymentOrder.getReason());
        operationRepository.save(operation);
    }
}
