package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.training.payments.adapters.input.rest.dto.CardTransactionRequestDto;
import pl.training.payments.AddCardTransactionService;

@RestController
@RequestMapping("api/cards")
public final class AddCardTransactionRestController {

    private final AddCardTransactionService addCardTransactionService;
    private final CardRestMapper mapper;

    public AddCardTransactionRestController(AddCardTransactionService addCardTransactionService, CardRestMapper mapper) {
        this.addCardTransactionService = addCardTransactionService;
        this.mapper = mapper;
    }

    @PostMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<Void> addCardTransaction(
            @PathVariable final String number,
            @Validated @RequestBody final CardTransactionRequestDto cardTransactionRequestDto) {
        var cardNumber = mapper.toDomain(number);
        var amount = mapper.toDomain(cardTransactionRequestDto);
        var transactionType = mapper.toDomain(cardTransactionRequestDto.getType());
        addCardTransactionService.addCardTransaction(cardNumber, amount, transactionType);
        return ResponseEntity.noContent().build();
    }

    /*@ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ExceptionDto> onCardNotFoundException(final CardNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto("Card not found"));
    }*/

}
