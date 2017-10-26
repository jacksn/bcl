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
                if (!paymentOrder.getSenderAccount().getStatus().equals(AccountStatus.ACTIVE)) {
                    paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                    paymentOrder.setRejectReason("Счет отправителя не активен.");
                } else if (!paymentOrder.getRecipientAccount().getStatus().equals(AccountStatus.ACTIVE)) {
                    paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                    paymentOrder.setRejectReason("Счет получателя не активен.");
                } else if (paymentOrder.getAmount()
                        .compareTo(accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(paymentOrder.getSenderAccount().getId())
                                .getAmount()) > 0) {
                    paymentOrder.setStatus(PaymentOrderStatus.REJECTED);
                    paymentOrder.setRejectReason("На счете отправителя недостаточно средств.");
                } else {
                    Operation operation = new Operation(
                            currentDate,
                            paymentOrder.getAmount(),
                            paymentOrder.getSenderAccount(),
                            paymentOrder.getRecipientAccount(),
                            paymentOrder.getReason());
                    operationRepository.save(operation);

                    AccountBalance senderBalance =
                            accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(paymentOrder.getSenderAccount().getId());
                    if (!senderBalance.getDate().isEqual(currentDate)) {
                        senderBalance = new AccountBalance(currentDate, senderBalance.getAmount(), senderBalance.getAccount());
                    }

                    AccountBalance recipientBalance =
                            accountBalanceRepository.getFirstByAccountIdOrderByDateDesc(paymentOrder.getRecipientAccount().getId());
                    if (!recipientBalance.getDate().isEqual(currentDate)) {
                        recipientBalance = new AccountBalance(currentDate, recipientBalance.getAmount(), recipientBalance.getAccount());
                    }

                    senderBalance.setAmount(senderBalance.getAmount().subtract(operation.getAmount()));
                    recipientBalance.setAmount(recipientBalance.getAmount().add(operation.getAmount()));

                    accountBalanceRepository.save(senderBalance);
                    accountBalanceRepository.save(recipientBalance);

                    paymentOrder.setStatus(PaymentOrderStatus.EXECUTED);
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
}
