package sample.Utils.DB_Provider;

import java.sql.SQLException;

/**
 * Created by VAN on 15.09.2016.
 */
public interface IDBConnection {
    boolean establishConnection () throws SQLException;
    void closeConnection()  throws SQLException;
    boolean isConnected();
    void setConnectionProp(DBConnProp connectionProp);
}
