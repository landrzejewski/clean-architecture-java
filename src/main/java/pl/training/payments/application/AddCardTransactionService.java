package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.payments.ports.input.AddCardTransaction;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardRepository;
import pl.training.payments.ports.output.TimeProvider;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.CardTransactionRegistered;
import pl.training.payments.domain.Money;

import java.util.function.Consumer;

import static pl.training.payments.domain.CardTransactionType.INFLOW;
import static pl.training.payments.domain.CardTransactionType.PAYMENT;

@Atomic
public class AddCardTransactionService implements AddCardTransaction {

    private final TimeProvider timeProvider;
    private final CardEventPublisher eventPublisher;
    private final CardRepository cardRepository;

    public AddCardTransactionService(final TimeProvider timeProvider, final CardEventPublisher eventPublisher, final CardRepository cardRepository) {
        this.timeProvider = timeProvider;
        this.eventPublisher = eventPublisher;
        this.cardRepository = cardRepository;
    }

    @Override
    public void addInflow(final CardNumber cardNumber, final Money amount) {
        addTransaction(cardNumber, new CardTransaction(timeProvider.getTimestamp(), amount, INFLOW));
    }

    @Override
    public void addPayment(final CardNumber cardNumber, final Money amount) {
        addTransaction(cardNumber, new CardTransaction(timeProvider.getTimestamp(), amount, PAYMENT));
    }

    private void addTransaction(final CardNumber cardNumber, final CardTransaction transaction) {
        var card = cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
        var eventListener = createCardEventListener();
        card.addEventsListener(eventListener);
        card.registerTransaction(transaction);
        card.removeEventsListener(eventListener);
        cardRepository.save(card);
    }

    private Consumer<CardTransactionRegistered> createCardEventListener() {
        return event -> {
            var appEvent = new CardTransactionEvent(event.cardNumber().toString(), event.cardTransaction().type().name());
            eventPublisher.publish(appEvent);
        };
    }

}
