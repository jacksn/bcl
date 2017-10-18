package ru.atc.bclient.to;

public enum NotificationType {
    INFO("info"),
    SUCCESS("success"),
    WARNING("warning"),
    ERROR("danger");

    private final String notificationClass;

    NotificationType(String notificationClass) {
        this.notificationClass = notificationClass;
    }

    @Override
    public String toString() {
        return notificationClass;
    }
}
