package ru.atc.bclient.model.entity;

public enum PaymentOrderStatus {
    NEW(1, "Новое"),
    ACCEPTED(2, "Принято"),
    CANCELLED(3, "Отменено"),
    IN_PROGRESS(4, "В обработке"),
    EXECUTED(5, "Исполнено"),
    REJECTED(6, "Отклонено");

    private final int id;
    private final String name;

    PaymentOrderStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PaymentOrderStatus of(int id) {
        switch (id) {
            case 1:
                return PaymentOrderStatus.NEW;
            case 2:
                return PaymentOrderStatus.ACCEPTED;
            case 3:
                return PaymentOrderStatus.CANCELLED;
            case 4:
                return PaymentOrderStatus.IN_PROGRESS;
            case 5:
                return PaymentOrderStatus.EXECUTED;
            case 6:
                return PaymentOrderStatus.REJECTED;
            default:
                throw new IllegalArgumentException("Не найден статус платежного поручения с id=" + id);
        }
    }

    public int getId() {
        return id;
    }
}
