package ru.atc.bclient.web.controller;

import org.springframework.validation.FieldError;

import java.util.List;

public class AbstractController {
    protected static String getFieldErrorMessages(List<FieldError> fieldErrors) {
        StringBuilder messages = new StringBuilder();
        for (FieldError error : fieldErrors) {
            messages.append("<strong>Поле ").append(error.getField()).append(":</strong> ")
                    .append(error.getDefaultMessage()).append(".<br/>");
        }
        return messages.toString();
    }
}
