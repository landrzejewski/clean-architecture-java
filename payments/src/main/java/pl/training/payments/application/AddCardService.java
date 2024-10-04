package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardId;
import pl.training.payments.domain.CardNumberGenerator;
import pl.training.payments.ports.output.CardOperations;
import pl.training.payments.ports.output.DateTimeProvider;

import java.time.LocalDate;
import java.util.Currency;

@Atomic
public class AddCardService {

    private static final int EXPIRATION_TIME_IN_YEARS = 1;

    private final CardNumberGenerator cardNumberGenerator;
    private final DateTimeProvider dateTimeProvider;
    private final CardOperations cardOperations;

    public AddCardService(final CardNumberGenerator cardNumberGenerator, final DateTimeProvider dateTimeProvider, final CardOperations cardOperations) {
        this.cardNumberGenerator = cardNumberGenerator;
        this.dateTimeProvider = dateTimeProvider;
        this.cardOperations = cardOperations;
    }

    public Card addCard(final Currency currency) {
        var card = new Card(new CardId(), cardNumberGenerator.getNext(), calculateExpirationDate(), currency);
        return cardOperations.save(card);
    }

    private LocalDate calculateExpirationDate() {
        return dateTimeProvider.getZonedDateTime()
                .plusYears(EXPIRATION_TIME_IN_YEARS)
                .toLocalDate();
    }

}
