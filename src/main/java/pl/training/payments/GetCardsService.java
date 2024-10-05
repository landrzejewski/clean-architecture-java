package pl.training.payments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.infrastruture.persistence.CardQueries;

@Transactional
@Service
public class GetCardsService {

    private final CardQueries cardQueries;

    public GetCardsService(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public ResultPage<Card> getCards(final PageSpec pageSpec) {
        return cardQueries.findAll(pageSpec);
    }

}
