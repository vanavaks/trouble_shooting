package sample.model.Mothers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by VAN on 22.09.2016.
 */
public @Retention(RetentionPolicy.RUNTIME)
@interface EditedFields {
    String[] value() default {};
}