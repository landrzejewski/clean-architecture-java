package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.AddCardUseCase;
import pl.training.payments.domain.Card;

import java.time.LocalDate;
import java.util.Currency;

import static pl.training.payments.adapters.common.web.LocationUri.fromCurrentRequestWith;
import static pl.training.payments.adapters.rest.AddCardResponse.from;

@RestController
final class AddCardRestController {

    private final AddCardUseCase addCardUseCase;

    AddCardRestController(final AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @PostMapping("api/cards")
    ResponseEntity<AddCardResponse> addCard(@RequestBody final AddCardRequest addCardRequest) {
        var card = addCardUseCase.handle(addCardRequest.currency());
        var locationUri = fromCurrentRequestWith(card.getNumber().value());
        return ResponseEntity.created(locationUri).body(from(card));
    }

}

record AddCardRequest(String currencyCode) {

    Currency currency() {
        return Currency.getInstance(currencyCode);
    }

}

record AddCardResponse(String number, LocalDate expiration, String currencyCode) {

    static AddCardResponse from(final Card card) {
        return new AddCardResponse(card.getNumber().value(), card.getExpiration(), card.getCurrency().getCurrencyCode());
    }

}
