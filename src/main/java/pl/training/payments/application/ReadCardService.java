package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.ports.input.GetCardUseCase;
import pl.training.payments.ports.output.CardQueries;

@Atomic
public class ReadCardService implements GetCardUseCase {

    private final CardQueries cardQueries;

    public ReadCardService(CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    @Override
    public Card getCard(final CardNumber cardNumber) {
        return cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
