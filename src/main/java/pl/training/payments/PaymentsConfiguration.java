package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.AddCardService;
import pl.training.payments.application.AddCardTransactionService;
import pl.training.payments.application.ReadCardService;
import pl.training.payments.application.ReadCardsService;
import pl.training.payments.domain.CardNumberGenerator;
import pl.training.payments.domain.RandomCardNumberGenerator;
import pl.training.payments.ports.input.AddCard;
import pl.training.payments.ports.input.AddCardTransaction;
import pl.training.payments.ports.input.ReadCard;
import pl.training.payments.ports.input.ReadCards;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardRepository;
import pl.training.payments.ports.output.TimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCard addCard(CardNumberGenerator cardNumberGenerator, TimeProvider timeProvider, CardRepository cardRepository) {
        return new AddCardService(cardNumberGenerator, timeProvider, cardRepository);
    }

    @Bean
    public CardNumberGenerator cardNumberGenerator() {
        return new RandomCardNumberGenerator(16);
    }

    @Bean
    public AddCardTransaction addCardTransaction(CardRepository cardRepository, TimeProvider timeProvider, CardEventPublisher eventPublisher) {
        return new AddCardTransactionService(timeProvider, eventPublisher, cardRepository);
    }

    @Bean
    public ReadCard readCard(CardRepository cardRepository) {
        return new ReadCardService(cardRepository);
    }

    @Bean
    public ReadCards readCards(CardRepository cardRepository) {
        return new ReadCardsService(cardRepository);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
