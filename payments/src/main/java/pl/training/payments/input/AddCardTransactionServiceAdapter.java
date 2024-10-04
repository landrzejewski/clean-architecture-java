package pl.training.payments.input;

import pl.training.common.model.Money;
import pl.training.payments.application.AddCardTransactionService;
import pl.training.payments.AddCardTransactionUseCase;
import pl.training.payments.CardTransactionType;

public class AddCardTransactionServiceAdapter implements AddCardTransactionUseCase {

    private final AddCardTransactionService addCardTransactionService;
    private final PaymentsMapper mapper;

    public AddCardTransactionServiceAdapter(AddCardTransactionService addCardTransactionService, PaymentsMapper mapper) {
        this.addCardTransactionService = addCardTransactionService;
        this.mapper = mapper;
    }

    @Override
    public void addCardTransaction(final String cardNumber, final Money amount, final CardTransactionType cardTransactionType) {
        var number = mapper.map(cardNumber);
        addCardTransactionService.addCardTransaction(number, amount, cardTransactionType);
    }

}
