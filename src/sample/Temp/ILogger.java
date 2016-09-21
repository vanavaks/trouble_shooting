package sample.Temp;

/**
 * Created by VAN on 20.09.2016.
 */
public interface ILogger {
    void info(String s);
    void warning(String s);
    void error(String s);
    void exception(String s, Exception e);
}
