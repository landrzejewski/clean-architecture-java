package pl.training.payments;

import java.util.function.Consumer;

public interface AddCardTransactionListenerUseCase {

    void addListener(Consumer<String> listener);

}
