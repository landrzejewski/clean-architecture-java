package pl.training.payments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.training.payments.infrastruture.persistence.CardQueries;

@Transactional
@Service
public class GetCardService {

    private final CardQueries cardQueries;

    public GetCardService(final CardQueries cardQueries) {
        this.cardQueries = cardQueries;
    }

    public Card getCard(final CardNumber cardNumber) {
        return cardQueries.findByNumber(cardNumber)
                .orElseThrow(CardNotFoundException::new);
    }

}
