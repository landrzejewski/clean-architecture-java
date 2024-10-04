package pl.training.orders;

import pl.training.common.model.Money;
import pl.training.payments.AddCardTransactionListenerUseCase;
import pl.training.payments.AddCardTransactionUseCase;

import java.util.Currency;
import java.util.logging.Logger;

import static pl.training.payments.CardTransactionType.PAYMENT;

public class OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderService.class.getName());

    private final AddCardTransactionUseCase addCardTransactionUseCase;

    public OrderService(AddCardTransactionUseCase addCardTransactionUseCase, AddCardTransactionListenerUseCase addCardTransactionListenerUseCase) {
        this.addCardTransactionUseCase = addCardTransactionUseCase;
        addCardTransactionListenerUseCase.addListener(this::confirmOrder);
    }

    public void placeOrder(String cardNumber) {
        LOGGER.info("Placing order");
        LOGGER.info("Executing payment transaction for card: " + cardNumber);
        var orderTotalValue = new Money(1, Currency.getInstance("PLN"));
        addCardTransactionUseCase.addCardTransaction(cardNumber, orderTotalValue, PAYMENT);
    }

    public void confirmOrder(String cardNumber) {
        LOGGER.info("Payment transaction for card %s confirmed".formatted(cardNumber));
    }

}
