package pl.training.payments.adapters.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.training.payments.application.AddCardUseCase;

import static pl.training.payments.adapters.common.web.LocationUri.fromCurrentRequestWith;

@RestController
public final class AddCardRestController {

    private final AddCardUseCase addCardUseCase;

    public AddCardRestController(AddCardUseCase addCardUseCase) {
        this.addCardUseCase = addCardUseCase;
    }

    @PostMapping("api/cards")
    public ResponseEntity<AddCardResponse> addCard(@RequestBody final AddCardCommand addCardCommand) {
        var card = addCardUseCase.handle(addCardCommand.currency());
        var locationUri = fromCurrentRequestWith(card.getNumber().value());
        var response = new AddCardResponse(card);
        return ResponseEntity.created(locationUri).body(response);
    }

}
