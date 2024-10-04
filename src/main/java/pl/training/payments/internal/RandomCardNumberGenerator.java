package pl.training.payments.internal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.training.payments.CardNumber;

import java.util.Random;

@Service
public final class RandomCardNumberGenerator implements CardNumberGenerator {

    private final Random random = new Random();
    private final int length;

    public RandomCardNumberGenerator(@Value("${cardNumber.length}") final int length) {
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
