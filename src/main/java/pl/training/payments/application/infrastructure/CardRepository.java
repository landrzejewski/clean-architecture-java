package pl.training.payments.application.infrastructure;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

import java.util.Optional;

public interface CardRepository {

    Card save(Card card);

    ResultPage<Card> findAll(PageSpec pageSpec);

    Optional<Card> findByNumber(CardNumber cardNumber);

}
