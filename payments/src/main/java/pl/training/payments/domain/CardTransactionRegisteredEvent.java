package pl.training.payments.domain;

public record CardTransactionRegisteredEvent(CardNumber cardNumber, CardTransaction cardTransaction) {
}
