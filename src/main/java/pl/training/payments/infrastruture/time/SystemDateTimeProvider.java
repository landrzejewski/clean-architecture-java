package pl.training.payments.infrastruture.time;

import pl.training.common.annotations.Adapter;

import java.time.ZonedDateTime;

@Adapter
public final class SystemDateTimeProvider implements DateTimeProvider {

    @Override
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.now();
    }

}
