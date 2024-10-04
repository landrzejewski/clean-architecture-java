package pl.training.payments.ports.output;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.ports.input.model.Card;
import pl.training.payments.ports.input.model.CardNumber;

import java.util.Optional;

public interface CardQueries {

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(CardNumber cardNumber);

}
