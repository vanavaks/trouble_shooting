package sample.Utils.DB_Provider;

import java.io.File;
import java.io.IOException;

/**
 * Created by VAN on 20.09.2016.
 */
public interface IDBParametersReader {
    DBConnProp read() throws Exception;
    void write(DBConnProp prop) throws Exception;
}
