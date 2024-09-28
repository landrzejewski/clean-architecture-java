package pl.training.payments.domain;

import java.util.UUID;

public record CardId(String value) {

    public CardId() {
        this(UUID.randomUUID().toString());
    }

}
