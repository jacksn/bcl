package ru.atc.bclient.model.entity;

public enum PaymentOrderStatus {
    NONE("Отсутствует"),
    NEW("Новое"),
    ACCEPTED("Принято"),
    CANCELLED("Отменено"),
    IN_PROGRESS("В обработке"),
    EXECUTED("Исполнено"),
    REJECTED("Отклонено");

    private final String name;

    PaymentOrderStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PaymentOrderStatus of(int id) {
        if (id < 1 || id > (values().length - 1)) {
            throw new IllegalArgumentException("Не найден статус платежного поручения с id=" + id);
        }
        return values()[id];
    }
}
