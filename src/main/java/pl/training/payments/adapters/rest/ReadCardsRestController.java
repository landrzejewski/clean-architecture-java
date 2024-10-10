package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.application.GetCardsUseCase;
import pl.training.payments.domain.Card;

import java.time.LocalDate;

@RestController
final class ReadCardsRestController {

    private final GetCardsUseCase getCardsUseCase;

    ReadCardsRestController(final GetCardsUseCase getCardsUseCase) {
        this.getCardsUseCase = getCardsUseCase;
    }

    @GetMapping("api/cards")
    ResponseEntity<ResultPage<ReadCardsResponse>> getCards(
            @RequestParam(required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(required = false, defaultValue = "10") final int pageSize) {
        var pageSpec = new PageSpec(pageNumber, pageSize);
        var result = getCardsUseCase.handle(pageSpec).map(ReadCardsResponse::from);
        return ResponseEntity.ok(result);
    }

}

record ReadCardsResponse(String number, LocalDate expiration, Double balance, String currencyCode) {

    static ReadCardsResponse from(Card card) {
        return new ReadCardsResponse(card.getNumber().value(), card.getExpiration(), card.getBalance().amount().doubleValue(), card.getCurrency().getCurrencyCode());
    }

}
