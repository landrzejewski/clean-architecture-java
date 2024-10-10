package pl.training.payments.adapters.rest;

import pl.training.payments.domain.CardTransaction;

import java.time.Instant;

public record ReadCardTransactionResponse(Double amount, String currencyCode, String type, Instant timestamp) {

    public ReadCardTransactionResponse(CardTransaction cardTransaction) {
        this(cardTransaction.money().amount().doubleValue(), cardTransaction.money().currency().getCurrencyCode(), switch (cardTransaction.type()) {
            case INFLOW -> "IN";
            case PAYMENT -> "OUT";
        }, cardTransaction.timestamp().toInstant());
    }

}
