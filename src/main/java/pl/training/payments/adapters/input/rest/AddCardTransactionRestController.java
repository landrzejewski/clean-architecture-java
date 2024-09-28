package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.training.payments.ports.input.AddCardTransaction;

@RestController
@RequestMapping("api/cards")
public final class AddCardTransactionRestController {

    private final AddCardTransaction addCardTransaction;
    private final CardRestMapper mapper;

    public AddCardTransactionRestController(final AddCardTransaction addCardTransaction, final CardRestMapper mapper) {
        this.addCardTransaction = addCardTransaction;
        this.mapper = mapper;
    }

    @PostMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<Void> addTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final CardTransactionRequestDto cardTransactionRequestDto) {
        var cardNumber = mapper.toDomain(number);
        var amount = mapper.toDomain(cardTransactionRequestDto);
        switch (cardTransactionRequestDto.getType()) {
            case INPUT -> addCardTransaction.addInflow(cardNumber, amount);
            case OUTPUT -> addCardTransaction.addPayment(cardNumber, amount);
        }
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}
