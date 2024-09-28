package pl.training.payments.ports.output;

import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

import java.util.Optional;

public interface CardRepository {

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(CardNumber cardNumber);

    Card save(Card card);

}
