package pl.training.payments.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.rest.dto.CardDto;
import pl.training.payments.rest.dto.CardTransactionDto;
import pl.training.payments.GetCardService;

import java.util.List;

@RestController
@RequestMapping("api/cards")
public final class ReadCardRestController {

    private final GetCardService getCardService;
    private final CardRestMapper mapper;

    public ReadCardRestController(GetCardService getCardService, CardRestMapper mapper) {
        this.getCardService = getCardService;
        this.mapper = mapper;
    }

    @GetMapping("{number:\\d{16,19}}")
    public ResponseEntity<CardDto> getCard(@PathVariable final String number) {
        var cardNumber = mapper.toDomain(number);
        var card = getCardService.getCard(cardNumber);
        var cardDto = mapper.toDto(card);
        return ResponseEntity.ok(cardDto);
    }

    @GetMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<List<CardTransactionDto>> getTransactions(@PathVariable final String number) {
        var cardNumber = mapper.toDomain(number);
        var transactions = getCardService.getCard(cardNumber).registeredTransactions();
        var transactionsDtos = mapper.toDto(transactions);
        return ResponseEntity.ok(transactionsDtos);
    }

}
