package training.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.training.orders.OrderService;
import pl.training.payments.AddCardTransactionListenerUseCase;
import pl.training.payments.AddCardTransactionUseCase;
import pl.training.payments.application.CardTransactionEventBusService;
import pl.training.payments.input.AddCardTransactionListenerServiceAdapter;

@Configuration
public class OrdersConfiguration {

    @Bean
    public OrderService orderService(AddCardTransactionUseCase addCardTransactionUseCase, AddCardTransactionListenerUseCase addCardTransactionListenerUseCase) {
        return new OrderService(addCardTransactionUseCase, addCardTransactionListenerUseCase);
    }

    @Bean
    public AddCardTransactionListenerUseCase addCardTransactionListenerUseCase(CardTransactionEventBusService cardTransactionEventBusService) {
        return new AddCardTransactionListenerServiceAdapter(cardTransactionEventBusService);
    }

    @Bean
    public CardTransactionEventBusService cardTransactionEventBusService() {
        return new CardTransactionEventBusService();
    }

}
