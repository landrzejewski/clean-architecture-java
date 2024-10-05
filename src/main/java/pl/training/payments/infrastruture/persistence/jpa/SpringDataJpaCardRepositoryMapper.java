package pl.training.payments.infrastruture.persistence.jpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.training.common.annotations.Mapper;
import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.payments.Card;
import pl.training.payments.CardId;
import pl.training.payments.CardNumber;
import pl.training.payments.CardTransaction;

import java.util.Currency;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Mapper
public final class SpringDataJpaCardRepositoryMapper {

    private final static ObjectMapper JSON_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    private final static TypeReference<List<CardTransaction>> TRANSACTION_LIST_TYPE = new TypeReference<>() {
    };

    public CardEntity toEntity(final Card card) {
        var cardEntity = new CardEntity();
        cardEntity.setId(card.getId().value());
        cardEntity.setNumber(toEntity(card.getNumber()));
        cardEntity.setExpiration(card.getExpiration());
        cardEntity.setCurrencyCode(card.getCurrency().getCurrencyCode());
        cardEntity.setTransactions(toJson(card.registeredTransactions()));
        return cardEntity;
    }

    public String toEntity(final CardNumber cardNumber) {
        return cardNumber.value();
    }

    public PageRequest toEntity(final PageSpec pageSpec) {
        return PageRequest.of(pageSpec.index(), pageSpec.size());
    }

    public Card toDomain(final CardEntity cardEntity) {
        var cardId = new CardId(cardEntity.getId());
        var cardNumber = new CardNumber(cardEntity.getNumber());
        var currency = Currency.getInstance(cardEntity.getCurrencyCode());
        var expiration = cardEntity.getExpiration();
        var card = new Card(cardId, cardNumber, expiration, currency);
        if (cardEntity.getTransactions() != null) {
            fromJson(cardEntity.getTransactions()).forEach(card::registerTransaction);
        }
        return card;
    }

    public ResultPage<Card> toDomain(final Page<CardEntity> page) {
        return new ResultPage<>(
                page.getContent().stream().map(this::toDomain).toList(),
                new PageSpec(page.getNumber(), page.getSize()),
                page.getTotalPages()
        );
    }

    private String toJson(final List<CardTransaction> transactions) {
        try {
            return JSON_MAPPER.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CardTransaction> fromJson(final String json) {
        try {
            return JSON_MAPPER.readValue(json, TRANSACTION_LIST_TYPE);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
