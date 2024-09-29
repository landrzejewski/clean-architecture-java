package training.payments.adapters.common.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice(annotations = RestController.class)
public final class GlobalRestExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(GlobalRestExceptionHandler.class.getName());
    private static final String KEY_VALUE_SEPARATOR = " - ";
    private static final String DELIMITER = ", ";

    private final RestExceptionResponseBuilder exceptionResponseBuilder;

    public GlobalRestExceptionHandler(final RestExceptionResponseBuilder exceptionResponseBuilder) {
        this.exceptionResponseBuilder = exceptionResponseBuilder;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> onException(final Exception exception, final Locale locale) {
        LOGGER.info("Exception occurred: " + exception);
        return exceptionResponseBuilder.build(exception, INTERNAL_SERVER_ERROR, locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> onMethodArgumentNotValid(final MethodArgumentNotValidException exception, final Locale locale) {
        var description = exceptionResponseBuilder.getLocalizedMessage(exception, locale, getValidationErrors(exception));
        return exceptionResponseBuilder.build(description, BAD_REQUEST);
    }

    private String getValidationErrors(final MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + KEY_VALUE_SEPARATOR + fieldError.getDefaultMessage())
                .collect(Collectors.joining(DELIMITER));
    }

}
