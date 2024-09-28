package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.training.payments.ports.input.AddCardTransactionUseCase;

@RestController
@RequestMapping("api/cards")
public final class AddCardTransactionRestController {

    private final AddCardTransactionUseCase addCardTransactionUseCase;
    private final CardRestMapper mapper;

    public AddCardTransactionRestController(final AddCardTransactionUseCase addCardTransaction, final CardRestMapper mapper) {
        this.addCardTransactionUseCase = addCardTransaction;
        this.mapper = mapper;
    }

    @PostMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<Void> addTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final CardTransactionRequestDto cardTransactionRequestDto) {
        var cardNumber = mapper.toDomain(number);
        var amount = mapper.toDomain(cardTransactionRequestDto);
        var transactionType = mapper.toDomain(cardTransactionRequestDto.getType());
        addCardTransactionUseCase.addCardTransaction(cardNumber, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}
