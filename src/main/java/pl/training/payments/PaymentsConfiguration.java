package pl.training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.AddCardService;
import pl.training.payments.application.AddCardTransactionService;
import pl.training.payments.application.ReadCardService;
import pl.training.payments.application.ReadCardsService;
import pl.training.payments.domain.CardNumberGenerator;
import pl.training.payments.domain.RandomCardNumberGenerator;
import pl.training.payments.ports.input.AddCardTransactionUseCase;
import pl.training.payments.ports.input.AddCardUseCase;
import pl.training.payments.ports.input.GetCardUseCase;
import pl.training.payments.ports.input.GetCardsUseCase;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardQueries;
import pl.training.payments.ports.output.CardOperations;
import pl.training.payments.ports.output.DateTimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCardUseCase addCardUseCase(CardNumberGenerator cardNumberGenerator, DateTimeProvider dateTimeProvider, CardOperations cardOperations) {
        return new AddCardService(cardNumberGenerator, dateTimeProvider, cardOperations);
    }

    @Bean
    public CardNumberGenerator cardNumberGenerator() {
        return new RandomCardNumberGenerator(16);
    }

    @Bean
    public AddCardTransactionUseCase addCardTransactionUseCase(DateTimeProvider dateTimeProvider, CardEventPublisher cardEventPublisher,
                                                               CardQueries cardQueries, CardOperations cardOperations) {
        return new AddCardTransactionService(dateTimeProvider, cardEventPublisher, cardQueries, cardOperations);
    }

    @Bean
    public GetCardUseCase readCardUseCase(CardQueries cardQueries) {
        return new ReadCardService(cardQueries);
    }

    @Bean
    public GetCardsUseCase readCardsUseCase(CardQueries cardQueries) {
        return new ReadCardsService(cardQueries);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.domain.Card pl.training.payments.application.ReadCardService.getCard(pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
