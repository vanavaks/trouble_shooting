package sample.model;

import java.awt.*;
import java.lang.reflect.Field;

/**
 * Created by Иван on 05.08.2016.
 * Class for validation of model
 */
public class Validator {

    public static <M> boolean isValid(Field f, Class<M> t){
        return true;
    }

    public static <M> boolean isValid(Class<M> t){
        return true;
    }

    public static <M> String vadidate(Class<M> t){
        return null;
    }

    public static <M> String validate(Field f, M model) {
        try {
            Object o = f.get(model);
            NotNull nnAnot = f.getAnnotation(NotNull.class);
            if (f.isAnnotationPresent(NotNull.class)) {
                try {
                    if (o instanceof String) {
                        String val = (String) f.get(model);
                        if (val.equals("")) {
                            return nnAnot.message();
                        }
                    } else if (o instanceof Integer) {
                        Integer val = (Integer) f.get(model);
                        if (val < 0) {
                            return nnAnot.message();
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <M> boolean isValid(Field f, Class<M> t, Label text){
        return true;
    }

    public static boolean isValidInt(String s){
        String regex = "[0-9]+";
        //if(s.equals(""))return true;
        return s.matches(regex);
    }
}
