package pl.training.payments.adapters.commons.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.Money;

import java.util.logging.Logger;

@Order(10000)
@Aspect
@Component
public class CardTransactionLoggingAspect {

    private static final Logger log = Logger.getLogger(CardTransactionLoggingAspect.class.getName());

    // @Pointcut("@annotation(pl.training.commons.annotations.Loggable)")
    @Pointcut("execution(void pl.training.payments.application.AddCardTransactionService.*(pl.training.payments.domain.CardNumber, pl.training.payments.domain.Money))")
    public void payment() {
    }

    @Before(value = "payment() && args(cardNumber, amount)", argNames = "joinPoint,cardNumber,amount")
    public void before(JoinPoint joinPoint, CardNumber cardNumber, Money amount) {
        log.info("New payment request for card: " + cardNumber);
    }

    @AfterReturning(value = "payment()")
    public void onSuccess() {
        log.info("Payment successful");
    }

    @AfterThrowing(value = "payment()", throwing = "runtimeException")
    public void onFailure(JoinPoint joinPoint, RuntimeException runtimeException) {
        log.info("Payment failed with exception: " + runtimeException.getClass().getSimpleName() +
                " (method: " + joinPoint.getSignature() + ")");
    }

    @After("payment()")
    public void afterCharge() {
        log.info("Payment processing complete");
    }

}
