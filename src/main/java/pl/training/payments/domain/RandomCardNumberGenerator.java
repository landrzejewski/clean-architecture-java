package pl.training.payments.domain;

import java.util.Random;

final class RandomCardNumberGenerator implements CardNumberGenerator {

    private final Random random = new Random();
    private final int length;

    public RandomCardNumberGenerator(final int length) {
        this.length = length;
    }

    @Override
    public CardNumber getNext() {
        var number = new StringBuilder();
        for (int index = 0; index < length; index++) {
            int digit = random.nextInt(10);
            number.append(digit);
        }
        return new CardNumber(number.toString());
    }

}
