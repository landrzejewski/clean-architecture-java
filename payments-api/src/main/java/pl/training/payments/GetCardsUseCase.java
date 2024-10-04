package pl.training.payments;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;

public interface GetCardsUseCase {

    ResultPage<CardInfo> getCards(PageSpec pageSpec);

}
