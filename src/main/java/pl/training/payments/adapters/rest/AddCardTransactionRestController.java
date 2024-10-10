package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.AddCardTransactionUseCase;
import pl.training.payments.domain.CardNumber;

@RestController
public final class AddCardTransactionRestController {

    private final AddCardTransactionUseCase addCardTransactionUseCase;

    public AddCardTransactionRestController(AddCardTransactionUseCase addCardTransactionUseCase) {
        this.addCardTransactionUseCase = addCardTransactionUseCase;
    }

    @PostMapping("api/cards/{number:\\d{16,19}}/transactions")
    public ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final AddCardTransactionCommand addCardTransactionCommand) {
        var cardNumber = new CardNumber(number);
        var amount = addCardTransactionCommand.money();
        var transactionType = addCardTransactionCommand.transactionType();
        addCardTransactionUseCase.handle(cardNumber, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}
