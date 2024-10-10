package pl.training.payments.adapters.rest;

import pl.training.payments.domain.CardTransactionType;
import pl.training.payments.domain.Money;

import java.util.Currency;

import static pl.training.payments.domain.CardTransactionType.INFLOW;
import static pl.training.payments.domain.CardTransactionType.PAYMENT;

public record AddCardTransactionCommand(Double amount, String currencyCode, String type) {

    public Money money() {
        var currency = Currency.getInstance(currencyCode);
        return new Money(amount, currency);
    }

    public CardTransactionType transactionType() {
        return switch (type) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
