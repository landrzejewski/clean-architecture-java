package pl.training.payments.ports.input;

import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.domain.Card;

public interface GetCardsUseCase {

    ResultPage<Card> getCards(PageSpec pageSpec);

}
