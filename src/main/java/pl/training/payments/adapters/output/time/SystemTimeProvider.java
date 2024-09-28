package pl.training.payments.adapters.output.time;

import pl.training.payments.adapters.commons.annotations.Adapter;
import pl.training.payments.ports.output.TimeProvider;

import java.time.ZonedDateTime;

@Adapter
public final class SystemTimeProvider implements TimeProvider {

    @Override
    public ZonedDateTime getTimestamp() {
        return ZonedDateTime.now();
    }

}
