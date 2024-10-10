package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.GetCardUseCase;
import pl.training.payments.domain.CardNumber;

import java.util.List;

@RestController
@RequestMapping("api/cards")
public final class ReadCardRestController {

    private final GetCardUseCase getCardUseCase;

    public ReadCardRestController(GetCardUseCase getCardUseCase) {
        this.getCardUseCase = getCardUseCase;
    }

    @GetMapping("{number:\\d{16,19}}")
    public ResponseEntity<ReadCardResponse> getCard(@PathVariable final String number) {
        var cardNumber = new CardNumber(number);
        var card = getCardUseCase.handle(cardNumber);
        var response = new ReadCardResponse(card);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{number:\\d{16,19}}/transactions")
    public ResponseEntity<List<ReadCardTransactionResponse>> getTransactions(@PathVariable final String number) {
        var cardNumber = new CardNumber(number);
        var card = getCardUseCase.handle(cardNumber);
        var result = card.registeredTransactions()
                .stream()
                .map(ReadCardTransactionResponse::new)
                .toList();
        return ResponseEntity.ok(result);
    }

}
