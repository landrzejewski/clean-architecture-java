package pl.training.payments.adapters.output.persistence.jpa;

import pl.training.common.model.PageSpec;
import pl.training.common.model.ResultPage;
import pl.training.common.annotations.Adapter;
import pl.training.payments.Card;
import pl.training.payments.CardNumber;
import pl.training.payments.adapters.output.persistence.CardOperations;
import pl.training.payments.adapters.output.persistence.CardQueries;

import java.util.Optional;

@Adapter
public final class SpringDataJpaCardRepositoryAdapter implements CardQueries, CardOperations {

    private final SpringDataJpaCardRepository repository;
    private final SpringDataJpaCardRepositoryMapper mapper;

    public SpringDataJpaCardRepositoryAdapter(final SpringDataJpaCardRepository repository, final SpringDataJpaCardRepositoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ResultPage<Card> findAll(final PageSpec pageSpec) {
        var pageRequest = mapper.toEntity(pageSpec);
        var cardEntitiesPage = repository.findAll(pageRequest);
        return mapper.toDomain(cardEntitiesPage);
    }

    @Override
    public Optional<Card> findByNumber(final CardNumber cardNumber) {
        return repository.findByNumber(mapper.toEntity(cardNumber))
                .map(mapper::toDomain);
    }

    @Override
    public Card save(final Card card) {
        var cardEntity = mapper.toEntity(card);
        var savedEntity = repository.save(cardEntity);
        return mapper.toDomain(savedEntity);
    }

}
