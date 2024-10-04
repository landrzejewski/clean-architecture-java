package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.ports.input.model.CardNotFoundException;
import pl.training.payments.ports.input.model.CardNumber;
import pl.training.payments.ports.input.model.CardTransactionType;
import pl.training.payments.domain.*;
import pl.training.payments.ports.input.AddCardTransactionUseCase;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardOperations;
import pl.training.payments.ports.output.CardQueries;
import pl.training.payments.ports.output.DateTimeProvider;

import java.util.function.Consumer;

@Atomic
public class AddCardTransactionService implements AddCardTransactionUseCase {

    private final DateTimeProvider dateTimeProvider;
    private final CardEventPublisher eventPublisher;
    private final CardQueries cardQueries;
    private final CardOperations cardOperations;

    public AddCardTransactionService(final DateTimeProvider dateTimeProvider, final CardEventPublisher cardEventPublisher,
                                     final CardQueries cardQueries, final CardOperations cardOperations) {
        this.dateTimeProvider = dateTimeProvider;
        this.eventPublisher = cardEventPublisher;
        this.cardQueries = cardQueries;
        this.cardOperations = cardOperations;
    }

    // @EnableLogging
    @Override
    public void addCardTransaction(final CardNumber cardNumber, final Money amount, final CardTransactionType cardTransactionType) {
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
