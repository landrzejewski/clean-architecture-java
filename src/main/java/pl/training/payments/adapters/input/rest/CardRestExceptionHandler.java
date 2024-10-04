package pl.training.payments.adapters.input.rest;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.training.common.web.ExceptionDto;
import pl.training.common.web.RestExceptionResponseBuilder;
import pl.training.payments.ports.input.model.CardNotFoundException;

import java.util.Locale;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = "pl.training.payments.adapters.input.rest")
public final class CardRestExceptionHandler {

    private final RestExceptionResponseBuilder exceptionResponseBuilder;

    public CardRestExceptionHandler(final RestExceptionResponseBuilder exceptionResponseBuilder) {
        this.exceptionResponseBuilder = exceptionResponseBuilder;
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception, final Locale locale) {
        return exceptionResponseBuilder.build(exception, NOT_FOUND, locale);
    }

}
