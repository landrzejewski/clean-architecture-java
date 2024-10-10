package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.application.infrastructure.CardQueries;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

@Atomic
public class GetCardUseCase {

    private final CardQueries cardQueries;

    public GetCardUseCase(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public Card handle(final CardNumber cardNumber) {
        return cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
