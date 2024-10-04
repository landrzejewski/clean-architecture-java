package training.orders;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.training.orders.OrderService;
import pl.training.payments.application.CardTransactionEvent;

@Component
public class OrdersTest implements ApplicationRunner {

    private final OrderService orderService;

    public OrdersTest(final OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        orderService.placeOrder("5929304441757200");
    }

    /*@EventListener
    public void onCardTransactionEvent(CardTransactionEvent cardTransactionEvent) {
        orderService.confirmOrder(cardTransactionEvent.cardNumber());
    }*/

}
