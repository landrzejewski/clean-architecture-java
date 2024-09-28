package pl.training.payments.application;

public record CardTransactionEvent(String cardNumber, String type) {
}
