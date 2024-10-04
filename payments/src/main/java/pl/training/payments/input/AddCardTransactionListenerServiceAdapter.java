package pl.training.payments.input;

import pl.training.payments.AddCardTransactionListenerUseCase;
import pl.training.payments.application.CardTransactionEventBusService;

import java.util.function.Consumer;

public class AddCardTransactionListenerServiceAdapter implements AddCardTransactionListenerUseCase {

    private final CardTransactionEventBusService cardTransactionEventBusService;

    public AddCardTransactionListenerServiceAdapter(final CardTransactionEventBusService cardTransactionEventBusService) {
        this.cardTransactionEventBusService = cardTransactionEventBusService;
    }

    @Override
    public void addListener(final Consumer<String> listener) {
        cardTransactionEventBusService.addListener(listener);
    }

}
