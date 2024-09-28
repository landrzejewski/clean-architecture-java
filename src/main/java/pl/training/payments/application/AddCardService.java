package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardId;
import pl.training.payments.domain.CardNumberGenerator;
import pl.training.payments.ports.input.AddCardUseCase;
import pl.training.payments.ports.output.CardUpdates;
import pl.training.payments.ports.output.TimeProvider;

import java.time.LocalDate;
import java.util.Currency;

@Atomic
public class AddCardService implements AddCardUseCase {

    private static final int DEFAULT_EXPIRATION_TIME_IN_YEARS = 1;

    private final CardNumberGenerator cardNumberGenerator;
    private final TimeProvider timeProvider;
    private final CardUpdates cardUpdates;

    public AddCardService(final CardNumberGenerator cardNumberGenerator, final TimeProvider timeProvider, final CardUpdates cardUpdates) {
        this.cardNumberGenerator = cardNumberGenerator;
        this.timeProvider = timeProvider;
        this.cardUpdates = cardUpdates;
    }

    @Override
    public Card addCard(final Currency currency) {
        var cardNumber = cardNumberGenerator.getNext();
        var expiration = createExpirationDate();
        var card = new Card(new CardId(), cardNumber, expiration, currency);
        return cardUpdates.save(card);
    }

    private LocalDate createExpirationDate() {
        return timeProvider.getTimestamp()
                .plusYears(DEFAULT_EXPIRATION_TIME_IN_YEARS)
                .toLocalDate();
    }

}
