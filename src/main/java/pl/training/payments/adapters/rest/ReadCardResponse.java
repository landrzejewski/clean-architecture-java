package pl.training.payments.adapters.rest;

import pl.training.payments.domain.Card;

import java.time.LocalDate;

public record ReadCardResponse(String number, LocalDate expiration, Double balance, String currencyCode) {

    public ReadCardResponse(Card card) {
        this(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}
