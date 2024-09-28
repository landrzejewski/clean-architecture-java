package pl.training.payments.adapters.input.rest;

import java.time.Instant;

public final class CardTransactionDto {

    private Double amount;
    private String currencyCode;
    private CardTransactionTypeDto type;
    private Instant timestamp;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public CardTransactionTypeDto getType() {
        return type;
    }

    public void setType(CardTransactionTypeDto type) {
        this.type = type;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

}
