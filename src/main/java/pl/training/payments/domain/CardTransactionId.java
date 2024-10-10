package pl.training.payments.domain;

import java.util.UUID;

public record CardTransactionId(String value) {

    public CardTransactionId() {
        this(UUID.randomUUID().toString());
    }

}
