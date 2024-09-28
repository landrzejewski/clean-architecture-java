package training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.application.AddCardService;
import pl.training.payments.application.AddCardTransactionService;
import pl.training.payments.application.GetCardService;
import pl.training.payments.application.GetCardsService;
import pl.training.payments.domain.DefaultCardNumberGeneratorFactory;
import pl.training.payments.ports.input.AddCardTransactionUseCase;
import pl.training.payments.ports.input.AddCardUseCase;
import pl.training.payments.ports.input.GetCardUseCase;
import pl.training.payments.ports.input.GetCardsUseCase;
import pl.training.payments.ports.output.CardEventPublisher;
import pl.training.payments.ports.output.CardOperations;
import pl.training.payments.ports.output.CardQueries;
import pl.training.payments.ports.output.DateTimeProvider;

@Configuration
public class PaymentsConfiguration {

    @Bean
    public AddCardUseCase addCardUseCase(DateTimeProvider dateTimeProvider, CardOperations cardOperations) {
        var cardNumberGenerator = new DefaultCardNumberGeneratorFactory().get();
        return new AddCardService(cardNumberGenerator, dateTimeProvider, cardOperations);
    }

    @Bean
    public AddCardTransactionUseCase addCardTransactionUseCase(DateTimeProvider dateTimeProvider, CardEventPublisher cardEventPublisher,
                                                               CardQueries cardQueries, CardOperations cardOperations) {
        return new AddCardTransactionService(dateTimeProvider, cardEventPublisher, cardQueries, cardOperations);
    }

    @Bean
    public GetCardUseCase getCardUseCase(CardQueries cardQueries) {
        return new GetCardService(cardQueries);
    }

    @Bean
    public GetCardsUseCase getCardsUseCase(CardQueries cardQueries) {
        return new GetCardsService(cardQueries);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
