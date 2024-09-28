package pl.training.payments.domain;

public final class DefaultCardNumberGeneratorFactory implements CardNumberGeneratorFactory {

    @Override
    public CardNumberGenerator get() {
        return new RandomCardNumberGenerator(16);
    }

}
