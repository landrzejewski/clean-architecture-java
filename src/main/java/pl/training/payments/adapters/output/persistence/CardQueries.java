package pl.training.payments.adapters.output.persistence;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.Card;
import pl.training.payments.CardNumber;

import java.util.Optional;

public interface CardQueries {

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(CardNumber cardNumber);

}
