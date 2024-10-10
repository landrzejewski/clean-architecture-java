package pl.training.payments.adapters.rest;

import pl.training.payments.domain.Card;

import java.time.LocalDate;

public record AddCardResponse(String number, LocalDate expiration, String currencyCode) {

    public AddCardResponse(Card card) {
        this(card.getNumber().value(), card.getExpiration(), card.getCurrency().getCurrencyCode());
    }

}
