package pl.training.payments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.common.model.Money;
import pl.training.payments.infrastruture.events.CardEventPublisher;
import pl.training.payments.infrastruture.persistence.CardOperations;
import pl.training.payments.infrastruture.persistence.CardQueries;
import pl.training.payments.infrastruture.time.DateTimeProvider;

import java.util.function.Consumer;

@Transactional
@Service
public class AddCardTransactionService {

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
