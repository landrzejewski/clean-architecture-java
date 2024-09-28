package pl.training.payments.ports.input;

import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransactionType;
import pl.training.payments.domain.Money;

public interface AddCardTransactionUseCase {

    void addCardTransaction(CardNumber cardNumber, Money amount, CardTransactionType cardTransactionType);

}
