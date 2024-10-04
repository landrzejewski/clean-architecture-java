package pl.training.payments.ports.input;

import pl.training.payments.ports.model.CardInfo;

public interface GetCardUseCase {

    CardInfo getCard(String cardNumber);

}
