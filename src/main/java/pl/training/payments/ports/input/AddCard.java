package pl.training.payments.ports.input;

import pl.training.payments.domain.Card;

import java.util.Currency;

public interface AddCard {

    Card addCard(Currency currency);

}
