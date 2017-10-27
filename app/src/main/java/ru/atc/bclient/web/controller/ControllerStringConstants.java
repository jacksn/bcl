package ru.atc.bclient.web.controller;

public final class ControllerStringConstants {
    static final String ATTRIBUTE_NOTIFICATION = "notification";
    static final String ATTRIBUTE_LEGAL_ENTITIES = "legalEntities";
    static final String ATTRIBUTE_CONTRACT = "contract";
    static final String ATTRIBUTE_CONTRACTS = "contracts";
    static final String ATTRIBUTE_PAYMENT_ORDER = "paymentOrder";
    static final String ATTRIBUTE_PAYMENT_ORDER_FORM_DATA = "paymentOrderFormData";
    static final String ATTRIBUTE_START_DATE = "startDate";
    static final String ATTRIBUTE_END_DATE = "endDate";
    static final String ATTRIBUTE_ACCOUNT = "account";
    static final String ATTRIBUTE_BALANCE = "balance";
    static final String ATTRIBUTE_PAYMENT_ORDERS = "paymentOrders";

    static final String MESSAGE_ERROR_CREATING_PAYMENT_ORDER = "Ошибка создания платежного поручения!<br/>";
    static final String MESSAGE_ERROR_CANCELLING_PAYMENT_ORDER = "Ошибка отмены платежного поручения!<br/>";
    static final String MESSAGE_PROCESSING_IN_PROGRESS = "Происходит обработка платежный поручений. Попробуйте снова через несколько минут";

    static final String MESSAGE_OPERATION_CREATE = "Создание";
    static final String MESSAGE_OPERATION_SAVE = "Сохранение";
    static final String MESSAGE_OPERATION_UPDATE = "Изменение";
    static final String MESSAGE_DENIED_OPERATION = " платежных поручений временно невозможно.<br/>";

    private ControllerStringConstants() {
    }
}
