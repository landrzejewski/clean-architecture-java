package pl.training.payments;

public record CardTransactionRegisteredEvent(CardNumber cardNumber, CardTransaction cardTransaction) {
}
