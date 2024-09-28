package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
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

    public AddCardTransactionService(DateTimeProvider dateTimeProvider, CardEventPublisher eventPublisher, CardQueries cardQueries, CardOperations cardOperations) {
        this.dateTimeProvider = dateTimeProvider;
        this.eventPublisher = eventPublisher;
        this.cardQueries = cardQueries;
        this.cardOperations = cardOperations;
    }

    @Override
    public void addCardTransaction(CardNumber cardNumber, final Money amount, CardTransactionType cardTransactionType) {
        var card = cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        var eventListener = createCardEventListener();
        card.addEventsListener(eventListener);
        var transaction = new CardTransaction(dateTimeProvider.getZonedDateTime(), amount, cardTransactionType);
        card.registerTransaction(transaction);
        card.removeEventsListener(eventListener);
        cardOperations.save(card);
    }

    private Consumer<CardTransactionRegisteredEvent> createCardEventListener() {
        return event -> {
            var appEvent = new CardTransactionEvent(event.cardNumber().toString(), event.cardTransaction().type().name());
            eventPublisher.publish(appEvent);
        };
    }

}
