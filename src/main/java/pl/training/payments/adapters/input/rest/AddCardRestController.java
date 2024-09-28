package pl.training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.adapters.commons.web.LocationUri;
import pl.training.payments.ports.input.AddCardUseCase;

@RestController
@RequestMapping("api/cards")
public final class AddCardRestController {

    private final AddCardUseCase addCardUseCase;
    private final CardRestMapper mapper;

    public AddCardRestController(AddCardUseCase addCard, CardRestMapper mapper) {
        this.addCardUseCase = addCard;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CardDto> addCard(@RequestBody NewCardDto newCardDto) {
        var currency = mapper.toDomain(newCardDto);
        var card = addCardUseCase.addCard(currency);
        var locationUri = LocationUri.fromCurrentRequestWith(card.getNumber());
        var cardDto = mapper.toDto(card);
        return ResponseEntity.created(locationUri).body(cardDto);
    }

}
