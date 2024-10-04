package pl.training.payments.input;

import pl.training.payments.application.AddCardService;
import pl.training.payments.AddCardUseCase;

import java.util.Currency;

public class AddCardServiceAdapter implements AddCardUseCase {

    private final AddCardService addCardService;

    public AddCardServiceAdapter(final AddCardService addCardService) {
        this.addCardService = addCardService;
    }

    @Override
    public String addCard(final Currency currency) {
        return addCardService.addCard(currency).getId().value();
    }

}
