package pl.training.payments.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.function.Consumer;

import static java.math.BigDecimal.ZERO;

public final class Card {

    private final CardId id;
    private final CardNumber number;
    private final LocalDate expiration;
    private final Currency currency;
    private final List<CardTransaction> transactions = new ArrayList<>();
    private final List<Consumer<CardTransactionRegisteredEvent>> eventListeners = new ArrayList<>();

    private Money balance;

    public Card(final CardId id, final CardNumber number, final LocalDate expiration, final Currency currency) {
        this.id = id;
        this.number = number;
        this.expiration = expiration;
        this.currency = currency;
        this.balance = new Money(ZERO, currency);
    }

    public CardId getId() {
        return id;
    }

    public CardNumber getNumber() {
        return number;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Money getBalance() {
        return balance;
    }

    public List<CardTransaction> registeredTransactions() {
        return new ArrayList<>(transactions);
    }

    public void registerTransaction(final CardTransaction transaction) {
        validate(transaction);
        commit(transaction);
        publish(transaction);
    }

    private void validate(final CardTransaction transaction) {
        if (!transaction.hasCurrency(currency)) {
            throw new CurrencyMismatchException();
        }
        if (!transaction.isBefore(expiration)) {
            throw new CardExpiredException();
        }
        if (transaction.isPayment() && !balance.isGreaterOrEqual(transaction.money())) {
            throw new InsufficientFundsException();
        }
    }

    private void commit(final CardTransaction transaction) {
        transactions.add(transaction);
        var amount = transaction.money();
        balance = switch (transaction.type()) {
            case INFLOW -> balance.add(amount);
            case PAYMENT -> balance.subtract(amount);
        };
    }

    private void publish(final CardTransaction transaction) {
        var event = new CardTransactionRegisteredEvent(number, transaction);
        eventListeners.forEach(listener -> listener.accept(event));
    }

    public void addEventsListener(final Consumer<CardTransactionRegisteredEvent> listener) {
        eventListeners.add(listener);
    }

    public void removeEventsListener(final Consumer<CardTransactionRegisteredEvent> listener) {
        eventListeners.remove(listener);
    }

}
