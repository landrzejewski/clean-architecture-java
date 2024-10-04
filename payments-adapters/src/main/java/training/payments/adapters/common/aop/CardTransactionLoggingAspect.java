package training.payments.adapters.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pl.training.payments.domain.CardNumber;
import pl.training.payments.domain.CardTransactionType;

import java.util.logging.Logger;

@Order(10000)
@Aspect
@Component
public final class CardTransactionLoggingAspect {

    private static final Logger log = Logger.getLogger(CardTransactionLoggingAspect.class.getName());

    //@Pointcut("@annotation(pl.training.common.annotations.EnableLogging)")
    @Pointcut("execution(void pl.training.payments.application.AddCardTransactionService.addCardTransaction(pl.training.payments.domain.CardNumber, pl.training.payments.domain.Money, pl.training.payments.domain.CardTransactionType))")
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
