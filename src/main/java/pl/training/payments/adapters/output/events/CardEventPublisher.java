package pl.training.payments.adapters.output.events;

import pl.training.payments.CardTransactionEvent;

public interface CardEventPublisher {

    void publish(CardTransactionEvent cardTransactionEvent);

}
