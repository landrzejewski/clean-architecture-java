package pl.training.payments.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.training.common.model.ResultPage;
import pl.training.payments.rest.dto.CardDto;
import pl.training.payments.GetCardsService;

@RestController
@RequestMapping("api/cards")
public final class ReadCardsRestController {

    private final GetCardsService getCardsService;
    private final CardRestMapper mapper;

    public ReadCardsRestController(GetCardsService getCardsService, CardRestMapper mapper) {
        this.getCardsService = getCardsService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ResultPage<CardDto>> getCards(
            @RequestParam(required = false, defaultValue = "0") final int pageNumber,
            @RequestParam(required = false, defaultValue = "10") final int pageSize) {
        var pageSpec = mapper.toDomain(pageNumber, pageSize);
        var cardsPage = getCardsService.getCards(pageSpec);
        var cardsPageDto = mapper.toDto(cardsPage);
        return ResponseEntity.ok(cardsPageDto);
    }

}
