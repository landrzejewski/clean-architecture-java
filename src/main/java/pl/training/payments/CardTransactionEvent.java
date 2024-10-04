package pl.training.payments;

public record CardTransactionEvent(String cardNumber, String transactionType) {
}
