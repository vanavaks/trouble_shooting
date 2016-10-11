package sample.Utils.DB_Provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by VAN on 15.09.2016.
 */
public class MySQL_Connection implements IDBConnection{
    private static MySQL_Connection instance = new MySQL_Connection();  //Singletone

    public Connection getConnection() {
        return connection;
    }

    private Connection connection;
    private DBConnProp connectionProp;
    private boolean connStatus;
    private final static int timeout = 10; //sec

    private MySQL_Connection() {
        connectionProp = new DBConnProp(); //set default prop
    }
    public static MySQL_Connection getInstance(){
            if(instance == null)
                instance = new MySQL_Connection();//Singletone
        return instance;
    }
    public boolean isConnected() {
        return connStatus;
    }

    public void setConnectionProp(DBConnProp connectionProp) {
        this.connectionProp = connectionProp;
    }

    public boolean establishConnection () throws SQLException {
        closeConnection();

        if (connectionProp.Assert()) {
            connection = DriverManager.getConnection(connectionProp.getUrl(), connectionProp.getLogin(), connectionProp.getPass());
            connStatus = connection.isValid(timeout);
        }
        return connStatus;
    }

    public void closeConnection()  throws SQLException{
        if(connection != null){
            if(!connection.isClosed()){
                connection.close();
                while(!connection.isClosed()){
                    connStatus = true;
                }
            }
            if(connection.isClosed()) connStatus = false;
        }
    }
}
