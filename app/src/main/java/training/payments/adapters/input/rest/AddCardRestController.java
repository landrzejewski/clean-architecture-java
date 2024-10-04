package training.payments.adapters.input.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.AddCardService;
import training.payments.adapters.input.rest.dto.CardDto;
import training.payments.adapters.input.rest.dto.NewCardDto;

import static training.payments.adapters.common.web.LocationUri.fromCurrentRequestWith;

@RestController
@RequestMapping("api/cards")
public final class AddCardRestController {

    private final AddCardService addCardService;
    private final CardRestMapper mapper;

    public AddCardRestController(AddCardService addCardService, CardRestMapper mapper) {
        this.addCardService = addCardService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<CardDto> addCard(@RequestBody final NewCardDto newCardDto) {
        var currency = mapper.toDomain(newCardDto);
        var card = addCardService.addCard(currency);
        var locationUri = fromCurrentRequestWith(card.getNumber().value());
        var cardDto = mapper.toDto(card);
        return ResponseEntity.created(locationUri).body(cardDto);
    }

}
