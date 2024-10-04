package pl.training.payments.ports.input;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.ports.model.CardInfo;

public interface GetCardsUseCase {

    ResultPage<CardInfo> getCards(PageSpec pageSpec);

}
