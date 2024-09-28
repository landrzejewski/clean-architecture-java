package pl.training.payments.adapters.commons.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import pl.training.commons.annotations.Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static pl.training.commons.annotations.Lock.LockType.WRITE;

@Aspect
@Component
public final class LockAspect {

    @Around("@annotation(lock)")
    public Object lock(final ProceedingJoinPoint joinPoint, final Lock lock) throws Throwable {
        var newLock = new ReentrantReadWriteLock();
        var targetLock = lock.value() == WRITE ? newLock.writeLock() : newLock.readLock();
        targetLock.lock();
        try {
            return joinPoint.proceed();
        } finally {
            targetLock.unlock();
        }
    }

}
