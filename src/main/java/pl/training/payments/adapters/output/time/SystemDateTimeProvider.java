package pl.training.payments.adapters.output.time;

import pl.training.common.annotations.Adapter;
import pl.training.payments.ports.output.DateTimeProvider;

import java.time.ZonedDateTime;

@Adapter
public final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
