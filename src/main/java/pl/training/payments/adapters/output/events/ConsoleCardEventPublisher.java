package pl.training.payments.adapters.output.events;

import pl.training.payments.adapters.common.annotations.Adapter;
import pl.training.payments.application.CardTransactionEvent;
import pl.training.payments.ports.output.CardEventPublisher;

import java.util.logging.Logger;

@Adapter
public final class ConsoleCardEventPublisher implements CardEventPublisher {

    private static final Logger LOGGER = Logger.getLogger(ConsoleCardEventPublisher.class.getName());

    @Override
    public void publish(final CardTransactionEvent cardTransactionEvent) {
        LOGGER.info("New %s transaction on card %s".formatted(cardTransactionEvent.transactionType(), cardTransactionEvent.cardNumber()));
    }

}
