package pl.training.payments.infrastruture.persistence.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringDataJpaCardRepository extends CrudRepository<CardEntity, String> {

    Page<CardEntity> findAll(Pageable pageable);

    Optional<CardEntity> findByNumber(String cardNumber);

}
