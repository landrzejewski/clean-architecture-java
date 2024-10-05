package pl.training.payments.infrastruture.events;

import pl.training.payments.CardTransactionEvent;

public interface CardEventPublisher {

    void publish(CardTransactionEvent cardTransactionEvent);

}
