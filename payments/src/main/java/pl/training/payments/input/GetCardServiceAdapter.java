package pl.training.payments.input;

import pl.training.payments.application.GetCardService;
import pl.training.payments.GetCardUseCase;
import pl.training.payments.CardInfo;

public class GetCardServiceAdapter implements GetCardUseCase {

    private final GetCardService getCardService;
    private final PaymentsMapper mapper;

    public GetCardServiceAdapter(final GetCardService getCardService, final PaymentsMapper mapper) {
        this.getCardService = getCardService;
        this.mapper = mapper;
    }

    @Override
    public CardInfo getCard(final String cardNumber) {
        var number = mapper.map(cardNumber);
        var card = getCardService.getCard(number);
        return mapper.map(card);
    }

}
