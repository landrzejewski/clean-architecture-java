package pl.training.payments.adapters.output.events;

import pl.training.payments.adapters.commons.annotations.Adapter;
import pl.training.payments.application.CardTransactionEvent;
import pl.training.payments.ports.output.CardEventPublisher;

import java.util.logging.Logger;

@Adapter
public final class ConsoleCardEventPublisher implements CardEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(ConsoleCardEventPublisher.class.getName());

    @Override
    public void publish(final CardTransactionEvent event) {
        LOGGER.info("New %s transaction on card %s".formatted(event.type(), event.cardNumber()));
    }

}
