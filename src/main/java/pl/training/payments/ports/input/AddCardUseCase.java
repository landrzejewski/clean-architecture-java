package pl.training.payments.ports.input;

import pl.training.payments.ports.input.model.Card;

import java.util.Currency;

public interface AddCardUseCase {

    Card addCard(Currency currency);

}
