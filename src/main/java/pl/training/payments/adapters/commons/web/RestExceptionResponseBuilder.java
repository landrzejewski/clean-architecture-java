package pl.training.payments.adapters.commons.web;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public final class RestExceptionResponseBuilder {

    private final MessageSource messageSource;

    private static final String DEFAULT_DESCRIPTION = "Unknown error";

    public RestExceptionResponseBuilder(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResponseEntity<ExceptionDto> build(final String description, final HttpStatus status) {
        return ResponseEntity.status(status).body(new ExceptionDto(description));
    }

    public ResponseEntity<ExceptionDto> build(final Exception exception, final HttpStatus status, final Locale locale) {
        return build(getLocalizedMessage(exception, locale), status);
    }

    public String getLocalizedMessage(final Exception exception, final Locale locale, final String... params) {
        return messageSource.getMessage(exception.getClass().getSimpleName(), params, DEFAULT_DESCRIPTION, locale);
    }

}
