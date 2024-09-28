package pl.training.payments.adapters.output.persistence;

import pl.training.payments.adapters.commons.annotations.Adapter;
import pl.training.commons.model.PageSpec;
import pl.training.commons.model.ResultPage;
import pl.training.payments.ports.output.CardRepository;
import pl.training.payments.domain.Card;
import pl.training.payments.domain.CardNumber;

import java.util.Optional;

@Adapter
public class SpringDataJpaCardRepositoryAdapter implements CardRepository {

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
        return repository.findByNumber(cardNumber.value())
                .map(mapper::toDomain);
    }

    @Override
    public Card save(final Card card) {
        var cardEntity = mapper.toEntity(card);
        var savedEntity = repository.save(cardEntity);
        return mapper.toDomain(savedEntity);
    }

}
