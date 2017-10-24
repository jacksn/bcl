package ru.atc.bclient.web.to;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Notification implements Serializable {
    private final NotificationType type;
    private final String message;
}
