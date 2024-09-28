package pl.training.payments.ports.output;

import pl.training.payments.application.CardTransactionEvent;

public interface CardEventPublisher {

    void publish(CardTransactionEvent event);

}
