package pl.training.payments.ports.input;

import pl.training.common.model.Money;
import pl.training.payments.ports.model.CardTransactionType;

public interface AddCardTransactionUseCase {

    void addCardTransaction(String cardNumber, Money amount, CardTransactionType cardTransactionType);

}
