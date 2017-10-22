package ru.atc.bclient.model.entity;

public enum AccountStatus {
    ACTIVE(1, "Активный"),
    LOCKED(2, "Заблокирован"),
    CLOSED(3, "Закрыт");

    private final int id;
    private final String name;

    AccountStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static AccountStatus of(int id) {
        switch (id) {
            case 1:
                return AccountStatus.ACTIVE;
            case 2:
                return AccountStatus.LOCKED;
            case 3:
                return AccountStatus.CLOSED;
            default:
                throw new IllegalArgumentException("Не найден статус счета с id=" + id);
        }
    }

    public int getId() {
        return id;
    }
}
