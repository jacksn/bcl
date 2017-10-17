package ru.atc.bclient.model;

public enum AccountStatus {
    NONE("Отсутствует"),
    ACTIVE("Активный"),
    LOCKED("Заблокирован"),
    CLOSED("Закрыт");

    private final String name;

    AccountStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static AccountStatus of(int id) {
        if (id < 1 || id > (values().length - 1)) {
            throw new IllegalArgumentException("Не найден статус счета с id=" + id);
        }
        return values()[id];
    }
}
