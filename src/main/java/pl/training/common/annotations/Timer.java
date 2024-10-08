package pl.training.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timer {

    TimeUnit value() default TimeUnit.NS;

    enum TimeUnit {

        NS, MS

    }

}
