package ru.atc.bclient.web.controller;

import org.springframework.validation.FieldError;

import java.util.List;

public class AbstractController {
    static final String ATTR_NOTIFICATION = "notification";
    static final String ATTR_LEGAL_ENTITIES = "legalEntities";
    static final String ATTR_CONTRACT = "contract";
    static final String ATTR_CONTRACTS = "contracts";
    static final String ATTR_PAYMENT_ORDER = "paymentOrder";
    static final String ATTR_PAYMENT_ORDERS = "paymentOrders";
    static final String ATTR_PAYMENT_ORDER_FORM_DATA = "paymentOrderFormData";
    static final String ATTR_DATE_START = "startDate";
    static final String ATTR_DATE_END = "endDate";
    static final String ATTR_ACCOUNT = "account";
    static final String ATTR_BALANCE = "balance";

    static final String MESSAGE_ERROR_CREATING_PAYMENT_ORDER = "Ошибка создания платежного поручения!<br/>";
    static final String MESSAGE_ERROR_CANCELLING_PAYMENT_ORDER = "Ошибка отмены платежного поручения!<br/>";
    static final String MESSAGE_PROCESSING_IN_PROGRESS = "Происходит обработка платежный поручений. Попробуйте снова через несколько минут";

    static final String MESSAGE_OPERATION_CREATE = "Создание";
    static final String MESSAGE_OPERATION_SAVE = "Сохранение";
    static final String MESSAGE_OPERATION_UPDATE = "Изменение";
    static final String MESSAGE_DENIED_OPERATION = " платежных поручений временно невозможно.<br/>";

    static final String REDIRECT = "redirect:";

    protected static String getFieldErrorMessages(List<FieldError> fieldErrors) {
        StringBuilder messages = new StringBuilder();
        for (FieldError error : fieldErrors) {
            messages.append("<strong>Поле ").append(error.getField()).append(":</strong> ")
                    .append(error.getDefaultMessage()).append(".<br/>");
        }
        return messages.toString();
    }
}
