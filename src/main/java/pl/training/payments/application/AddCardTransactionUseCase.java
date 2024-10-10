package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.domain.*;
import pl.training.payments.application.infrastructure.CardEventPublisher;
import pl.training.payments.application.infrastructure.CardOperations;
import pl.training.payments.application.infrastructure.CardQueries;
import pl.training.payments.application.infrastructure.DateTimeProvider;

import java.util.function.Consumer;

@Atomic
public class AddCardTransactionUseCase {

    private final DateTimeProvider dateTimeProvider;
    private final CardEventPublisher eventPublisher;
    private final CardQueries cardQueries;
    private final CardOperations cardOperations;

    public AddCardTransactionUseCase(final DateTimeProvider dateTimeProvider, final CardEventPublisher cardEventPublisher,
                                     final CardQueries cardQueries, final CardOperations cardOperations) {
        this.dateTimeProvider = dateTimeProvider;
        this.eventPublisher = cardEventPublisher;
        this.cardQueries = cardQueries;
        this.cardOperations = cardOperations;
    }

    // @EnableLogging
    public void handle(final CardNumber cardNumber, final Money amount, final CardTransactionType cardTransactionType) {
        var card = cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        var cardEventListener = createCardEventListener();
        card.addEventsListener(cardEventListener);
        var transaction = new CardTransaction(dateTimeProvider.getZonedDateTime(), amount, cardTransactionType);
        card.registerTransaction(transaction);
        card.removeEventsListener(cardEventListener);
        cardOperations.save(card);
    }

    private Consumer<CardTransactionRegisteredEvent> createCardEventListener() {
        return event -> {
            var appEvent = new CardTransactionEvent(event.cardNumber().toString(), event.cardTransaction().type().name());
            eventPublisher.publish(appEvent);
        };
    }

}
