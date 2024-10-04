package pl.training.payments.adapters;

import pl.training.common.model.Money;
import pl.training.payments.application.AddCardTransactionService;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.ports.input.AddCardTransactionUseCase;
import pl.training.payments.ports.model.CardTransactionType;

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
