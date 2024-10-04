package pl.training.payments;

import pl.training.common.model.Money;

public interface AddCardTransactionUseCase {

    void addCardTransaction(String cardNumber, Money amount, CardTransactionType cardTransactionType);

}
