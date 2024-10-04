package pl.training.payments.adapters.persistence;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.training.payments.adapters.output.persistence.jpa.SpringDataJpaCardRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static pl.training.payments.CardTestFixtures.validCardEntity;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
@ExtendWith(SpringExtension.class)
class SpringDataJpaCardRepositoryTest {

    @Autowired
    private SpringDataJpaCardRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void given_card_exists_when_find_by_id_should_return_card() {
        var cardEntity = validCardEntity();
        entityManager.persist(cardEntity);
        entityManager.flush();
        assertEquals(cardEntity, repository.findByNumber(cardEntity.getNumber()).get());
    }

}
