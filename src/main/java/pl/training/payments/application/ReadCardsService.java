package pl.training.payments.application;

import pl.training.commons.annotations.Atomic;
import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.ports.input.ReadCards;
import pl.training.payments.ports.output.CardRepository;

@Atomic
public class ReadCardsService implements ReadCards {

    private final CardRepository cardRepository;

    public ReadCardsService(final CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public ResultPage<Card> getCards(final PageSpec pageSpec) {
        return cardRepository.findAll(pageSpec);
    }

}
