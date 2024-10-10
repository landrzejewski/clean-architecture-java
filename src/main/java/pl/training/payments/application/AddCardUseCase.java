package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.application.infrastructure.CardRepository;
import pl.training.payments.application.infrastructure.DateTimeProvider;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardId;
import pl.training.payments.domain.CardNumberGenerator;

import java.time.LocalDate;
import java.util.Currency;

@Atomic
public class AddCardUseCase {

    private static final int EXPIRATION_TIME_IN_YEARS = 1;

    private final CardNumberGenerator cardNumberGenerator;
    private final DateTimeProvider dateTimeProvider;
    private final CardRepository cardRepository;

    public AddCardUseCase(final CardNumberGenerator cardNumberGenerator, final DateTimeProvider dateTimeProvider, final CardRepository cardRepository) {
        this.cardNumberGenerator = cardNumberGenerator;
        this.dateTimeProvider = dateTimeProvider;
        this.cardRepository = cardRepository;
    }

    public Card handle(final Currency currency) {
        var card = new Card(new CardId(), cardNumberGenerator.getNext(), calculateExpirationDate(), currency);
        return cardRepository.save(card);
    }

    private LocalDate calculateExpirationDate() {
        return dateTimeProvider.getZonedDateTime()
                .plusYears(EXPIRATION_TIME_IN_YEARS)
                .toLocalDate();
    }

}
