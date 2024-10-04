package pl.training.payments.ports.output;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;

import java.util.Optional;

public interface CardQueries {

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(String cardNumber);

}
