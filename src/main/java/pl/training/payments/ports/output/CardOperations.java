package pl.training.payments.ports.output;

import pl.training.payments.ports.input.model.Card;

public interface CardOperations {

    Card save(Card card);

}
