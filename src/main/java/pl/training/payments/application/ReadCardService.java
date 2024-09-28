package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.ports.input.ReadCard;
import pl.training.payments.ports.output.CardRepository;

@Atomic
public class ReadCardService implements ReadCard {

    private final CardRepository cardRepository;

    public ReadCardService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card getCard(final CardNumber cardNumber) {
        return cardRepository.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
