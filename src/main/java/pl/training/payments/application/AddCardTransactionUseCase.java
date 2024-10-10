package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.application.infrastructure.CardEventPublisher;
import pl.training.payments.application.infrastructure.CardRepository;
import pl.training.payments.application.infrastructure.DateTimeProvider;
import pl.training.payments.domain.*;

import java.util.function.Consumer;

@Atomic
public class AddCardTransactionUseCase {

    private final DateTimeProvider dateTimeProvider;
    private final CardEventPublisher eventPublisher;
    private final CardRepository cardRepository;

    public AddCardTransactionUseCase(final DateTimeProvider dateTimeProvider, final CardEventPublisher cardEventPublisher,
                                    final CardRepository cardRepository) {
        this.dateTimeProvider = dateTimeProvider;
        this.eventPublisher = cardEventPublisher;
        this.cardRepository = cardRepository;
    }

    // @EnableLogging
    public void handle(final CardNumber cardNumber, final Money amount, final CardTransactionType cardTransactionType) {
        var card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        var cardEventListener = createCardEventListener();
        card.addEventsListener(cardEventListener);
        var transaction = new CardTransaction(new CardTransactionId(), dateTimeProvider.getZonedDateTime(), amount, cardTransactionType);
        card.registerTransaction(transaction);
        card.removeEventsListener(cardEventListener);
        cardRepository.save(card);
    }

    private Consumer<CardTransactionRegisteredEvent> createCardEventListener() {
        return event -> {
            var appEvent = new CardTransactionEvent(event.cardNumber().toString(), event.cardTransaction().type().name());
            eventPublisher.publish(appEvent);
        };
    }

}
