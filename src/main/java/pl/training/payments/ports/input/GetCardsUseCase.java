package pl.training.payments.ports.input;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;

public interface GetCardsUseCase {

    ResultPage<Card> getCards(PageSpec pageSpec);

}
