package sample.Utils.Validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by VAN on 22.09.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNull{
    String message();
}