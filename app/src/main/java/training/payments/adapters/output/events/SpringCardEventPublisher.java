package training.payments.adapters.output.events;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.training.payments.application.CardTransactionEvent;
import pl.training.payments.output.CardEventPublisher;

@Component
public class SpringCardEventPublisher implements CardEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringCardEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publish(CardTransactionEvent cardTransactionEvent) {
        eventPublisher.publishEvent(cardTransactionEvent);
    }

}
