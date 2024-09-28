package pl.training.payments.ports.input;

import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.Money;

public interface AddCardTransaction {

    void addInflow(CardNumber cardNumber, Money amount);

    void addPayment(CardNumber cardNumber, Money amount);

}
