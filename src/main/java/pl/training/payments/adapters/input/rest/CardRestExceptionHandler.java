package pl.training.payments.adapters.input.rest;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.payments.adapters.commons.web.ExceptionDto;
import pl.training.payments.adapters.commons.web.RestExceptionResponseBuilder;
import pl.training.payments.application.CardNotFoundException;

import java.util.Locale;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = "pl.training.payments.adapters.input.rest")
public final class CardRestExceptionHandler {

    private final RestExceptionResponseBuilder exceptionResponseBuilder;

    public CardRestExceptionHandler(final RestExceptionResponseBuilder exceptionResponseBuilder) {
        this.exceptionResponseBuilder = exceptionResponseBuilder;
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(CardNotFoundException exception, Locale locale) {
        return exceptionResponseBuilder.build(exception, HttpStatus.NOT_FOUND, locale);
    }

}
