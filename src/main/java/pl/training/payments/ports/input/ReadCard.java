package pl.training.payments.ports.input;

import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

public interface ReadCard {

    Card getCard(CardNumber cardNumber);

}
