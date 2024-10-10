package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.payments.application.infrastructure.CardRepository;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

@Atomic
public class GetCardUseCase {

    private final CardRepository cardRepository;

    public GetCardUseCase(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card handle(final CardNumber cardNumber) {
        return cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
