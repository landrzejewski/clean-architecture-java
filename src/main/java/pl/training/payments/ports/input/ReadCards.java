package pl.training.payments.ports.input;

import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.domain.Card;

public interface ReadCards {

    ResultPage<Card> getCards(PageSpec pageSpec);

}
