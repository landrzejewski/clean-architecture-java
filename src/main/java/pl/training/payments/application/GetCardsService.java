package pl.training.payments.application;

import pl.training.common.annotations.Atomic;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.ports.input.GetCardsUseCase;
import pl.training.payments.ports.output.CardQueries;

@Atomic
public class GetCardsService implements GetCardsUseCase {

    private final CardQueries cardQueries;

    public GetCardsService(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    @Override
    public ResultPage<Card> getCards(final PageSpec pageSpec) {
        return cardQueries.findAll(pageSpec);
    }

}
