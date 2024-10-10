package pl.training.payments.adapters.rest;

import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.AddCardTransactionUseCase;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransactionType;
import pl.training.payments.domain.Money;

import java.util.Currency;

import static pl.training.payments.domain.CardTransactionType.INFLOW;
import static pl.training.payments.domain.CardTransactionType.PAYMENT;

@RestController
final class AddCardTransactionRestController {

    private final AddCardTransactionUseCase addCardTransactionUseCase;

    AddCardTransactionRestController(final AddCardTransactionUseCase addCardTransactionUseCase) {
        this.addCardTransactionUseCase = addCardTransactionUseCase;
    }

    @PostMapping("api/cards/{number:\\d{16,19}}/transactions")
    ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final AddCardTransactionRequest addCardTransactionRequest) {
        var cardNumber = new CardNumber(number);
        var amount = addCardTransactionRequest.money();
        var transactionType = addCardTransactionRequest.transactionType();
        addCardTransactionUseCase.handle(cardNumber, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}

record AddCardTransactionRequest(@Min(100) Double amount, String currencyCode, String type) {

    Money money() {
        var currency = Currency.getInstance(currencyCode);
        return new Money(amount, currency);
    }

    CardTransactionType transactionType() {
        return switch (type) {
            case "IN" -> INFLOW;
            case "OUT" -> PAYMENT;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

}
