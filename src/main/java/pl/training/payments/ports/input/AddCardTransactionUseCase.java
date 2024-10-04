package pl.training.payments.ports.input;

import pl.training.payments.ports.input.model.CardNumber;
import pl.training.payments.ports.input.model.CardTransactionType;
import pl.training.common.model.Money;

public interface AddCardTransactionUseCase {

    void addCardTransaction(CardNumber cardNumber, Money amount, CardTransactionType cardTransactionType);

}
