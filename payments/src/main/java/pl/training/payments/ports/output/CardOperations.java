package pl.training.payments.ports.output;

import pl.training.payments.domain.Card;

public interface CardOperations {

    Card save(Card card);

}
