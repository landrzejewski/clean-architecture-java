package pl.training.payments.adapters;

import pl.training.common.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.ports.model.CardInfo;

public class PaymentsMapper {

    CardInfo map(Card card) {
        return new CardInfo(
                card.getId().value(),
                card.getNumber().value(),
                card.getExpiration(),
                card.getCurrency(),
                card.getBalance()
        );
    }

    ResultPage<CardInfo> map(ResultPage<Card> cards) {
        return new ResultPage<>(
                cards.content().stream().map(this::map).toList(),
                cards.pageSpec(),
                cards.totalPages()
        );
    }

    CardNumber map(String number) {
        return new CardNumber(number);
    }

}
