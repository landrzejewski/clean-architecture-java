package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.output.CardQueries;

@Atomic
public class GetCardsService {

    private final CardQueries cardQueries;

    public GetCardsService(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public ResultPage<Card> getCards(final PageSpec pageSpec) {
        return cardQueries.findAll(pageSpec);
    }

}
