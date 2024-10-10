package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.AddCardTransactionUseCase;
import pl.training.payments.application.AddCardUseCase;
import pl.training.payments.application.GetCardUseCase;
import pl.training.payments.application.GetCardsUseCase;
import pl.training.payments.application.infrastructure.CardEventPublisher;
import pl.training.payments.application.infrastructure.CardOperations;
import pl.training.payments.application.infrastructure.CardQueries;
import pl.training.payments.application.infrastructure.DateTimeProvider;
import pl.training.payments.domain.DefaultCardNumberGeneratorFactory;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCardUseCase addCardService(DateTimeProvider dateTimeProvider, CardOperations cardOperations) {
        var cardNumberGenerator = new DefaultCardNumberGeneratorFactory().get();
        return new AddCardUseCase(cardNumberGenerator, dateTimeProvider, cardOperations);
    }

    @Bean
    public AddCardTransactionUseCase addCardTransactionService(DateTimeProvider dateTimeProvider, CardEventPublisher cardEventPublisher,
                                                               CardQueries cardQueries, CardOperations cardOperations) {
        return new AddCardTransactionUseCase(dateTimeProvider, cardEventPublisher, cardQueries, cardOperations);
    }

    @Bean
    public GetCardUseCase getCardService(CardQueries cardQueries) {
        return new GetCardUseCase(cardQueries);
    }

    @Bean
    public GetCardsUseCase getCardsService(CardQueries cardQueries) {
        return new GetCardsUseCase(cardQueries);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
