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
}
