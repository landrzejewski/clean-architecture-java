package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.training.commons.model.ResultPage;
import pl.training.payments.ports.input.GetCardsUseCase;

@RestController
@RequestMapping("api/cards")
public final class ReadCardsRestController {

    private final GetCardsUseCase getCardsUseCase;
    private final CardRestMapper mapper;

    public ReadCardsRestController(final GetCardsUseCase getCardsUseCase, final CardRestMapper mapper) {
        this.getCardsUseCase = getCardsUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ResultPage<CardDto>> getCards(
            @RequestParam(required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(required = false, defaultValue = "10") final int pageSize) {
        var pageSpec = mapper.toDomain(pageNumber, pageSize);
        var cardsPage = getCardsUseCase.getCards(pageSpec);
        var cardsPageDto = mapper.toDto(cardsPage);
        return ResponseEntity.ok(cardsPageDto);
    }

}
