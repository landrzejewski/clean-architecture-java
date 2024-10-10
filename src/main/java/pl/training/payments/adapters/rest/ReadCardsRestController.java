package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.application.GetCardsUseCase;

@RestController
public final class ReadCardsRestController {

    private final GetCardsUseCase getCardsUseCase;

    public ReadCardsRestController(GetCardsUseCase getCardsUseCase) {
        this.getCardsUseCase = getCardsUseCase;
    }

    @GetMapping("api/cards")
    public ResponseEntity<ResultPage<ReadCardsResponse>> getCards(
            @RequestParam(required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(required = false, defaultValue = "10") final int pageSize) {
        var pageSpec = new PageSpec(pageNumber, pageSize);
        var cardsPage = getCardsUseCase.handle(pageSpec);
        var result = new ResultPage<>(
                cardsPage.content().stream().map(ReadCardsResponse::new).toList(),
                pageSpec, cardsPage.totalPages()
        );
        return ResponseEntity.ok(result);
    }

}
