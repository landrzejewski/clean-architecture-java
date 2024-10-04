package training.payments.adapters.output.time;

import pl.training.payments.ports.output.DateTimeProvider;
import training.payments.adapters.common.annotations.Adapter;

import java.time.ZonedDateTime;

@Adapter
public final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
