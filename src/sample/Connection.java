package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Иван on 10.04.2016.
 */
class MySQLConnection {
    private Connection connection;
    private final String url = "jdbc:mysql://localhost:3306/troubleshooting";
    private final String login = "root";
    private final String pass = "admin";


    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void establishConnection (){
        try {
            connection = DriverManager.getConnection(url, login, pass);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
