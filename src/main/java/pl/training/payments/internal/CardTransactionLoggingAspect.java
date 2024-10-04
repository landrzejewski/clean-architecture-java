package pl.training.payments.internal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.CardNumber;
import pl.training.payments.CardTransactionType;
import pl.training.common.model.Money;

import java.util.logging.Logger;

@Order(10000)
@Aspect
@Component
public final class CardTransactionLoggingAspect {

    private static final Logger log = Logger.getLogger(CardTransactionLoggingAspect.class.getName());

    //@Pointcut("@annotation(pl.training.common.annotations.EnableLogging)")
    @Pointcut("execution(void pl.training.payments.AddCardTransactionService.addCardTransaction(pl.training.payments.CardNumber, pl.training.common.model.Money, pl.training.payments.CardTransactionType))")
    public void transaction() {
    }

    @Before(value = "transaction() && args(cardNumber, amount, type)", argNames = "joinPoint,cardNumber,amount,type")
    public void before(final JoinPoint joinPoint, final CardNumber cardNumber, final Money amount, final CardTransactionType type) {
        log.info("New transaction request for card: " + cardNumber);
    }

    @AfterReturning(value = "transaction()")
    public void onSuccess() {
        log.info("Transaction successful");
    }

    @AfterThrowing(value = "transaction()", throwing = "runtimeException")
    public void onFailure(final JoinPoint joinPoint, final RuntimeException runtimeException) {
        log.info("Transaction failed with exception: " + runtimeException.getClass().getSimpleName() +
                " (method: " + joinPoint.getSignature() + ")");
    }

    @After("transaction()")
    public void onComplete() {
        log.info("Transaction processing complete");
    }

}
