package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.ports.input.GetCardUseCase;

import java.util.List;

@RestController
@RequestMapping("api/cards")
public final class ReadCardRestController {

    private final GetCardUseCase getCardUseCase;
    private final CardRestMapper mapper;

    public ReadCardRestController(final GetCardUseCase readCard, final CardRestMapper mapper) {
        this.getCardUseCase = readCard;
        this.mapper = mapper;
    }

    @GetMapping("{number:\\d{16,19}}")
    public ResponseEntity<CardDto> getCard(@PathVariable final String number) {
        var cardNumber = mapper.toDomain(number);
        var transactions = getCardUseCase.getCard(cardNumber);
        var transactionsDtos = mapper.toDto(transactions);
        return ResponseEntity.ok(transactionsDtos);
    }

    @GetMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<List<CardTransactionDto>> getTransactions(@PathVariable final String number) {
        var cardNumber = mapper.toDomain(number);
        var transactions = getCardUseCase.getCard(cardNumber).registeredTransactions();
        var transactionsDtos = mapper.toDto(transactions);
        return ResponseEntity.ok(transactionsDtos);
    }

}
