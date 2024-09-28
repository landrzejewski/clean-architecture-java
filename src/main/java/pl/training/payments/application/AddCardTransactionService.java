package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.payments.ports.input.AddCardTransactionUseCase;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardQueries;
import pl.training.payments.ports.output.CardUpdates;
import pl.training.payments.ports.output.TimeProvider;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.CardTransactionRegistered;
import pl.training.payments.domain.Money;

import java.util.function.Consumer;

import static pl.training.payments.domain.CardTransactionType.INFLOW;
import static pl.training.payments.domain.CardTransactionType.PAYMENT;

@Atomic
public class AddCardTransactionService implements AddCardTransactionUseCase {

    private final TimeProvider timeProvider;
    private final CardEventPublisher eventPublisher;
    private final CardQueries cardQueries;
    private final CardUpdates cardUpdates;

    public AddCardTransactionService(TimeProvider timeProvider, CardEventPublisher eventPublisher, CardQueries cardQueries, CardUpdates cardUpdates) {
        this.timeProvider = timeProvider;
        this.eventPublisher = eventPublisher;
        this.cardQueries = cardQueries;
        this.cardUpdates = cardUpdates;
    }

    @Override
    public void addInflowTransaction(final CardNumber cardNumber, final Money amount) {
        addTransaction(cardNumber, new CardTransaction(timeProvider.getTimestamp(), amount, INFLOW));
    }

    @Override
    public void addPaymentTransaction(final CardNumber cardNumber, final Money amount) {
        addTransaction(cardNumber, new CardTransaction(timeProvider.getTimestamp(), amount, PAYMENT));
    }

    private void addTransaction(final CardNumber cardNumber, final CardTransaction transaction) {
        var card = cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        var eventListener = createCardEventListener();
        card.addEventsListener(eventListener);
        card.registerTransaction(transaction);
        card.removeEventsListener(eventListener);
        cardUpdates.save(card);
    }

    private Consumer<CardTransactionRegistered> createCardEventListener() {
        return event -> {
            var appEvent = new CardTransactionEvent(event.cardNumber().toString(), event.cardTransaction().type().name());
            eventPublisher.publish(appEvent);
        };
    }

}
