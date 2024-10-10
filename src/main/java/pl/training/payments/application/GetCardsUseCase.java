package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.application.infrastructure.CardQueries;
import pl.training.payments.domain.Card;

@Atomic
public class GetCardsUseCase {

    private final CardQueries cardQueries;

    public GetCardsUseCase(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public ResultPage<Card> handle(final PageSpec pageSpec) {
        return cardQueries.findAll(pageSpec);
    }

}
