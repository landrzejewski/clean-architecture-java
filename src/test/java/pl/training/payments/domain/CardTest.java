package pl.training.payments.domain;

import org.junit.jupiter.api.Test;
import pl.training.payments.CardTestFixtures;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.training.payments.domain.CardTransactionType.INFLOW;
import static pl.training.payments.domain.CardTransactionType.PAYMENT;

class CardTest {

    private final Card card = CardTestFixtures.validCard();
    private final ZonedDateTime testDateTime = CardTestFixtures.testDateTime;
    private final Money testMoney = CardTestFixtures.testMoney;

    @Test
    void when_register_inflow_transaction_should_increase_balance() {
        var inflowTransaction = new CardTransaction(testDateTime, testMoney, INFLOW);
        card.registerTransaction(inflowTransaction);
        assertEquals(testMoney, card.getBalance());
    }

    @Test
    void given_not_enough_funds_on_card_when_register_payment_transaction_should_throw_insufficient_funds_exception() {
        var paymentTransaction = new CardTransaction(testDateTime, testMoney, PAYMENT);
        assertThrows(InsufficientFundsException.class, () -> card.registerTransaction(paymentTransaction));
    }

}
