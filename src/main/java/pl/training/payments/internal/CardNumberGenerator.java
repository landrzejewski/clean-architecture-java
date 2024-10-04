package pl.training.payments.internal;

import pl.training.payments.CardNumber;

public interface CardNumberGenerator {

    CardNumber getNext();

}
