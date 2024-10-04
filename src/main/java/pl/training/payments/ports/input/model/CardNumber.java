package pl.training.payments.ports.input.model;

public record CardNumber(String value) {

    private static final String NUMBER_PATTERN = "\\d{16,19}";

    public CardNumber {
        if (!value.matches(NUMBER_PATTERN)) {
            throw new IllegalArgumentException("Invalid card number");
        }
    }

}
