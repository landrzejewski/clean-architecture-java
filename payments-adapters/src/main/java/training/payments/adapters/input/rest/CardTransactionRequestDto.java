package training.payments.adapters.input.rest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import training.payments.adapters.commons.validation.Range;

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

}
