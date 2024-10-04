package pl.training.common.model;

import java.math.BigDecimal;
import java.util.Currency;

import static java.math.BigDecimal.ZERO;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

    public Money(final double amount, final Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }

    public Money add(final Money money) {
        checkCurrencyCompatibility(money);
        return new Money(amount.add(money.amount), currency);
    }

    public Money subtract(final Money money) {
        checkCurrencyCompatibility(money);
        return new Money(amount.subtract(money.amount), currency);
    }

    public boolean isGreaterOrEqual(final Money money) {
        checkCurrencyCompatibility(money);
        return amount.compareTo(money.amount) >= 0;
    }

    private void checkCurrencyCompatibility(final Money money) {
        if (!currency.equals(money.currency)) {
            throw new CurrencyMismatchException();
        }
    }

}
