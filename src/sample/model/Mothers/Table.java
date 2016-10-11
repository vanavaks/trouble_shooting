package sample.model.Mothers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by VAN on 22.09.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    String title();
    String table();
}
