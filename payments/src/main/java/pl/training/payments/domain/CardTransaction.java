package pl.training.payments.domain;

import pl.training.common.model.Money;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Currency;

import static pl.training.payments.domain.CardTransactionType.PAYMENT;

public record CardTransaction(ZonedDateTime timestamp, Money money, CardTransactionType type) {

    public boolean hasCurrency(final Currency currency) {
        return money.currency().equals(currency);
    }

    public boolean isBefore(final LocalDate localDate) {
        return timestamp.toLocalDate().isBefore(localDate);
    }

    public boolean isPayment() {
        return type == PAYMENT;
    }

}
