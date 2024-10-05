package pl.training.payments.infrastruture.time;

import java.time.ZonedDateTime;

public interface DateTimeProvider {

    ZonedDateTime getZonedDateTime();

}
