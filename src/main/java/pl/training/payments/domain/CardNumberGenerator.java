package pl.training.payments.domain;

import pl.training.payments.ports.input.model.CardNumber;

public interface CardNumberGenerator {

    CardNumber getNext();

}
