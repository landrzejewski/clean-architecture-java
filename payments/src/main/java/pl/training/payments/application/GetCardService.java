package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.ports.input.GetCardUseCase;
import pl.training.payments.ports.output.CardQueries;

@Atomic
public class GetCardService {

    private final CardQueries cardQueries;

    public GetCardService(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public Card getCard(final CardNumber cardNumber) {
        return cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
