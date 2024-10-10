package pl.training.payments.application.infrastructure;

import java.time.ZonedDateTime;

public interface DateTimeProvider {

    ZonedDateTime getZonedDateTime();

}
