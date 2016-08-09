package sample.model;

/**
 * Created by Иван on 09.08.2016.
 */
public interface Gettable<T extends dialogableModelDB> {
    T createNew();
}
