package pl.training.payments.adapters.input.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import pl.training.payments.adapters.commons.validation.Range;

public final class CardTransactionRequestDto {

    @NotNull
    @Range(minValue = 100.0)
    private Double amount;
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String currencyCode;
    private CardTransactionTypeDto type;

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

}
