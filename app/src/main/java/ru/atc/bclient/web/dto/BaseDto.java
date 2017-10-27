package ru.atc.bclient.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto {
    @Getter
    @Setter
    private Integer id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}
