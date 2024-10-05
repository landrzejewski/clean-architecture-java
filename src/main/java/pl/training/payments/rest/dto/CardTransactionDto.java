package pl.training.payments.rest.dto;

import java.time.Instant;

public final class CardTransactionDto {

    private Double amount;
    private String currencyCode;
    private CardTransactionTypeDto type;
    private Instant timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CardTransactionTypeDto getType() {
        return type;
    }

    public void setType(final CardTransactionTypeDto type) {
        this.type = type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Instant timestamp) {
        this.timestamp = timestamp;
    }

}
