package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.GetCardUseCase;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static pl.training.common.model.Mappers.mapList;
import static pl.training.payments.adapters.rest.ReadCardResponse.from;

@RestController
@RequestMapping("api/cards")
final class ReadCardRestController {

    private final GetCardUseCase getCardUseCase;

    ReadCardRestController(final GetCardUseCase getCardUseCase) {
        this.getCardUseCase = getCardUseCase;
    }

    @GetMapping("{number:\\d{16,19}}")
    ResponseEntity<ReadCardResponse> getCard(@PathVariable final String number) {
        var card = handle(number);
        return ResponseEntity.ok(from(card));
    }

    @GetMapping("{number:\\d{16,19}}/transactions")
    ResponseEntity<List<ReadCardTransactionResponse>> getTransactions(@PathVariable final String number) {
        var result = mapList(handle(number).registeredTransactions(), ReadCardTransactionResponse::from);
        return ResponseEntity.ok(result);
    }

    private Card handle(final String number) {
        var cardNumber = new CardNumber(number);
        return getCardUseCase.handle(cardNumber);
    }

}

record ReadCardResponse(String number, LocalDate expiration, Double balance, String currencyCode) {

    static ReadCardResponse from(Card card) {
        return new ReadCardResponse(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}

record ReadCardTransactionResponse(Double amount, String currencyCode, String type, Instant timestamp) {

    static ReadCardTransactionResponse from(CardTransaction cardTransaction) {
        return new ReadCardTransactionResponse(cardTransaction.money().amount().doubleValue(), cardTransaction.money().currency().getCurrencyCode(), switch (cardTransaction.type()) {
            case INFLOW -> "IN";
            case PAYMENT -> "OUT";
        }, cardTransaction.timestamp().toInstant());
    }

}
