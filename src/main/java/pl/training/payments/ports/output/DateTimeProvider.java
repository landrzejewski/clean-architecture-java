package pl.training.payments.ports.output;

import java.time.ZonedDateTime;

public interface DateTimeProvider {

    ZonedDateTime getZonedDateTime();

}
