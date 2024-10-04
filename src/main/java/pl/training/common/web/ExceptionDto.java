package pl.training.common.web;

import java.time.ZonedDateTime;

public record ExceptionDto(String message, ZonedDateTime timestamp) {

    public ExceptionDto(String message) {
        this(message, ZonedDateTime.now());
    }

}
