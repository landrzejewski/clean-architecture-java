package pl.training.payments.adapters.rest;

import java.util.Currency;

public record AddCardCommand(String currencyCode) {

    public Currency currency() {
        return Currency.getInstance(currencyCode);
    }

}
