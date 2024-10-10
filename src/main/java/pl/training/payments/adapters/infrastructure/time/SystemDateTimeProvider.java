package pl.training.payments.adapters.infrastructure.time;

import pl.training.payments.adapters.common.annotations.Adapter;
import pl.training.payments.application.infrastructure.DateTimeProvider;

import java.time.ZonedDateTime;

@Adapter
public final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
