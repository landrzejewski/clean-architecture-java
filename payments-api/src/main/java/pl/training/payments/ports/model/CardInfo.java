package pl.training.payments.ports.model;

import pl.training.common.model.Money;

import java.time.LocalDate;
import java.util.Currency;

public record CardInfo(String id, String number, LocalDate expiration, Currency currency, Money balance) {
}
