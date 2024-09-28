package pl.training.payments.domain;

public class DefaultCardNumberGeneratorFactory implements CardNumberGeneratorFactory {

    @Override
    public CardNumberGenerator get() {
        return new RandomCardNumberGenerator(16);
    }

}
