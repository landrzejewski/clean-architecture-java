package pl.training.payments.domain;

import pl.training.payments.ports.input.model.CardNumber;

public record CardTransactionRegisteredEvent(CardNumber cardNumber, CardTransaction cardTransaction) {
}
