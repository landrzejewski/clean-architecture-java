package training.payments.adapters.input.rest;

import pl.training.common.model.Money;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.domain.*;
import pl.training.payments.CardTransactionType;
import training.payments.adapters.common.annotations.Mapper;
import training.payments.adapters.input.rest.dto.*;

import java.util.Currency;
import java.util.List;

import static pl.training.payments.CardTransactionType.INFLOW;
import static pl.training.payments.CardTransactionType.PAYMENT;

@Mapper
public final class CardRestMapper {

    public CardNumber toDomain(final String number) {
        return new CardNumber(number);
    }

    public Currency toDomain(final NewCardDto newCardDto) {
        return Currency.getInstance(newCardDto.getCurrencyCode());
    }

    public Money toDomain(final CardTransactionRequestDto cardTransactionRequestDto) {
        var currency = Currency.getInstance(cardTransactionRequestDto.getCurrencyCode());
        return new Money(cardTransactionRequestDto.getAmount(), currency);
    }

    public CardTransactionType toDomain(final CardTransactionTypeDto cardTransactionTypeDto) {
        return switch (cardTransactionTypeDto) {
            case IN -> INFLOW;
            case OUT -> PAYMENT;
        };
    }

    public PageSpec toDomain(final int pageNumber, final int pageSize) {
        return new PageSpec(pageNumber, pageSize);
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
        cardTransactionDto.setType(toDto(cardTransaction.type()));
        cardTransactionDto.setTimestamp(cardTransaction.timestamp().toInstant());
        return cardTransactionDto;
    }

    public CardTransactionTypeDto toDto(final CardTransactionType cardTransactionType) {
        return switch (cardTransactionType) {
            case INFLOW -> CardTransactionTypeDto.IN;
            case PAYMENT -> CardTransactionTypeDto.OUT;
        };
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

}
