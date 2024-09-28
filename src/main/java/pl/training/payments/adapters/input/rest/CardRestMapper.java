package pl.training.payments.adapters.input.rest;

import pl.training.payments.adapters.commons.annotations.Mapper;
import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransaction;
import pl.training.payments.domain.Money;

import java.util.Currency;
import java.util.List;

@Mapper
public class CardRestMapper {

    public CardNumber toDomain(final String number) {
        return new CardNumber(number);
    }

    public Currency toDomain(final NewCardDto newCardDto) {
        return Currency.getInstance(newCardDto.getCurrencyCode());
    }

    public Money toDomain(final CardTransactionRequestDto cardTransactionRequestDto) {
        var curreny = Currency.getInstance(cardTransactionRequestDto.getCurrencyCode());
        return new Money(cardTransactionRequestDto.getAmount(), curreny);
    }

    public PageSpec toDomain(final int pageNumber, final int pageSize) {
        return new PageSpec(pageNumber, pageSize);
    }

    public ResultPage<CardDto> toDto(final ResultPage<Card> resultPage) {
        var cardDtos = resultPage.content().stream()
                .map(this::toDto)
                .toList();
        return new ResultPage<>(cardDtos, resultPage.pageSpec(), resultPage.totalPages());
    }

    public List<CardTransactionDto> toDto(final List<CardTransaction> transactions) {
        return transactions.stream()
                .map(this::toDto)
                .toList();
    }

    public CardDto toDto(final Card card) {
        var cardDto = new CardDto();
        cardDto.setNumber(card.getNumber().value());
        cardDto.setExpiration(card.getExpiration());
        cardDto.setBalance(card.getBalance().amount().doubleValue());
        cardDto.setCurrencyCode(card.getBalance().currency().getCurrencyCode());
        return cardDto;
    }

    private CardTransactionDto toDto(final CardTransaction cardTransaction) {
        var cardTransactionDto = new CardTransactionDto();
        cardTransactionDto.setAmount(cardTransaction.money().amount().doubleValue());
        cardTransactionDto.setCurrencyCode(cardTransaction.money().currency().getCurrencyCode());
        cardTransactionDto.setType(switch (cardTransaction.type()) {
            case INFLOW -> CardTransactionTypeDto.INPUT;
            case PAYMENT -> CardTransactionTypeDto.OUTPUT;
        });
        cardTransactionDto.setTimestamp(cardTransaction.timestamp().toInstant());
        return cardTransactionDto;
    }

}
