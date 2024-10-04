package pl.training.payments;

import pl.training.payments.adapters.output.persistence.CardEntity;
import pl.training.payments.ports.input.model.Card;
import pl.training.payments.ports.input.model.CardNumber;
import pl.training.payments.domain.CardId;
import pl.training.common.model.Money;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Currency;

public class CardTestFixtures {

    public static final ZonedDateTime testDateTime = ZonedDateTime.now();
    public static final Currency testCurrency = Currency.getInstance("PLN");
    public static final Money testMoney = new Money(100.0, testCurrency);
    public static final CardId testCardId = new CardId();
    public static final CardNumber testCardNumber = new CardNumber("4237251412344005");
    public static final LocalDate testExpirationDate = LocalDate.now().plusYears(1);

    public static Card validCard() {
        return new Card(testCardId, testCardNumber, testExpirationDate, testCurrency);
    }

    public static CardEntity validCardEntity() {
        var entity = new CardEntity();
        entity.setId(testCardId.value());
        entity.setNumber(testCardNumber.value());
        entity.setExpiration(testExpirationDate);
        entity.setCurrencyCode(testCurrency.getCurrencyCode());
        return entity;
    }

}
