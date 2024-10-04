package pl.training.payments.adapters;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.application.GetCardsService;
import pl.training.payments.ports.input.GetCardsUseCase;
import pl.training.payments.ports.model.CardInfo;

public class GetCardsServiceAdapter implements GetCardsUseCase {

    private final GetCardsService getCardsService;
    private final PaymentsMapper mapper;

    public GetCardsServiceAdapter(final GetCardsService getCardsService, final PaymentsMapper mapper) {
        this.getCardsService = getCardsService;
        this.mapper = mapper;
    }

    @Override
    public ResultPage<CardInfo> getCards(final PageSpec pageSpec) {
        var cards = getCardsService.getCards(pageSpec);
        return mapper.map(cards);
    }

}
