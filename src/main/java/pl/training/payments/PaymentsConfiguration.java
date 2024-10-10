package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.AddCardTransactionUseCase;
import pl.training.payments.application.AddCardUseCase;
import pl.training.payments.application.GetCardUseCase;
import pl.training.payments.application.GetCardsUseCase;
import pl.training.payments.application.infrastructure.CardEventPublisher;
import pl.training.payments.application.infrastructure.CardRepository;
import pl.training.payments.application.infrastructure.DateTimeProvider;
import pl.training.payments.domain.DefaultCardNumberGeneratorFactory;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCardUseCase addCardUseCase(DateTimeProvider dateTimeProvider, CardRepository cardRepository) {
        var cardNumberGenerator = new DefaultCardNumberGeneratorFactory().get();
        return new AddCardUseCase(cardNumberGenerator, dateTimeProvider, cardRepository);
    }

    @Bean
    public AddCardTransactionUseCase addCardTransactionUseCase(DateTimeProvider dateTimeProvider, CardEventPublisher cardEventPublisher,
                                                               CardRepository cardRepository) {
        return new AddCardTransactionUseCase(dateTimeProvider, cardEventPublisher, cardRepository);
    }

    @Bean
    public GetCardUseCase getCardUseCase(CardRepository cardRepository) {
        return new GetCardUseCase(cardRepository);
    }

    @Bean
    public GetCardsUseCase getCardsUseCase(CardRepository cardRepository) {
        return new GetCardsUseCase(cardRepository);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
