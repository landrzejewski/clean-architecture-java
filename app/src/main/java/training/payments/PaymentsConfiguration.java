package training.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.payments.AddCardTransactionListenerUseCase;
import pl.training.payments.application.*;
import pl.training.payments.domain.DefaultCardNumberGeneratorFactory;
import pl.training.payments.input.*;
import pl.training.payments.output.CardEventPublisher;
import pl.training.payments.output.CardOperations;
import pl.training.payments.output.CardQueries;
import pl.training.payments.output.DateTimeProvider;

@Configuration(proxyBeanMethods=false)
public class PaymentsConfiguration {

    @Bean
    public PaymentsMapper paymentsMapper() {
        return new PaymentsMapper();
    }

    @Bean
    public AddCardService addCardService(DateTimeProvider dateTimeProvider, CardOperations cardOperations) {
        var cardNumberGenerator = new DefaultCardNumberGeneratorFactory().get();
        return new AddCardService(cardNumberGenerator, dateTimeProvider, cardOperations);
    }

    @Bean
    public AddCardServiceAdapter addCardServiceAdapter(AddCardService addCardService) {
        return new AddCardServiceAdapter(addCardService);
    }

    @Bean
    public AddCardTransactionService addCardTransactionService(DateTimeProvider dateTimeProvider, CardEventPublisher cardEventPublisher,
                                                               CardQueries cardQueries, CardOperations cardOperations,
                                                               CardTransactionEventBusService cardTransactionEventBusService) {
        return new AddCardTransactionService(dateTimeProvider, cardEventPublisher, cardQueries, cardOperations, cardTransactionEventBusService);
    }

    @Bean
    public AddCardTransactionServiceAdapter addCardTransactionServiceAdapter(AddCardTransactionService addCardTransactionService, PaymentsMapper paymentsMapper) {
        return new AddCardTransactionServiceAdapter(addCardTransactionService, paymentsMapper);
    }

    @Bean
    public GetCardService getCardService(CardQueries cardQueries) {
        return new GetCardService(cardQueries);
    }

    @Bean
    public GetCardServiceAdapter getCardServiceAdapter(GetCardService getCardService, PaymentsMapper paymentsMapper) {
        return new GetCardServiceAdapter(getCardService, paymentsMapper);
    }

    @Bean
    public GetCardsService getCardsService(CardQueries cardQueries) {
        return new GetCardsService(cardQueries);
    }

    @Bean
    public GetCardsServiceAdapter getCardsServiceAdapter(GetCardsService getCardsService, PaymentsMapper paymentsMapper) {
        return new GetCardsServiceAdapter(getCardsService, paymentsMapper);
    }

    /*@Bean
    public Advisor cacheAdvisor(CacheAspect cacheAspect) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(pl.training.payments.pl.training.payments.domain.Card pl.training.payments.application.GetCardService.getCard(pl.training.payments.pl.training.payments.domain.CardNumber))");
        return new DefaultPointcutAdvisor(pointcut, cacheAspect);
    }*/

}
