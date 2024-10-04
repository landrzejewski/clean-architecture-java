package pl.training.payments.internal;

import org.junit.jupiter.api.Test;
import pl.training.payments.InsufficientFundsException;
import pl.training.payments.Card;
import pl.training.payments.CardTestFixtures;
import pl.training.payments.CardTransaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.training.payments.CardTestFixtures.testDateTime;
import static pl.training.payments.CardTestFixtures.testMoney;
import static pl.training.payments.CardTransactionType.INFLOW;
import static pl.training.payments.CardTransactionType.PAYMENT;

class CardTest {

    private final Card card = CardTestFixtures.validCard();

    @Test
    void when_register_inflow_transaction_should_increase_balance() {
        var inflow = new CardTransaction(testDateTime, testMoney, INFLOW);
        card.registerTransaction(inflow);
        assertEquals(testMoney, card.getBalance());
    }

    @Test
    void given_not_enough_funds_on_card_when_register_payment_transaction_should_throw_insufficient_funds_exception() {
        var payment = new CardTransaction(testDateTime, testMoney, PAYMENT);
        assertThrows(InsufficientFundsException.class, () -> card.registerTransaction(payment));
    }

}
