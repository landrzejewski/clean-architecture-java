package pl.training.payments.ports.output;

import pl.training.payments.domain.Card;

public interface CardUpdates {

    Card save(Card card);

}
