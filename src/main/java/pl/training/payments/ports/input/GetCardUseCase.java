package pl.training.payments.ports.input;

import pl.training.payments.ports.input.model.Card;
import pl.training.payments.ports.input.model.CardNumber;

public interface GetCardUseCase {

    Card getCard(CardNumber cardNumber);

}
