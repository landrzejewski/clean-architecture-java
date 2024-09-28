package pl.training.payments.ports.input;

import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.Money;

public interface AddCardTransactionUseCase {

    void addInflowTransaction(CardNumber cardNumber, Money amount);

    void addPaymentTransaction(CardNumber cardNumber, Money amount);

}
