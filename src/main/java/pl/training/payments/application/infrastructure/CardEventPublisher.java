package pl.training.payments.application.infrastructure;

import pl.training.payments.application.CardTransactionEvent;

public interface CardEventPublisher {

    void publish(CardTransactionEvent cardTransactionEvent);

}
