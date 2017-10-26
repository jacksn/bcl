package ru.atc.bclient.service;

public interface PaymentOrderProcessor {
    void process();

    Boolean isProcessingInProgress();
}
