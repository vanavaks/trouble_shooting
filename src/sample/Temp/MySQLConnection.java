package sample.Temp;

import sample.Utils.XML_parser.JaxbParser;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Иван on 10.04.2016.
 * Pattern Singletone
 */
@XmlRootElement //(name = "DataBase")
@XmlType(propOrder = {"url","login","pass"})
public class MySQLConnection {
    private final static MySQLConnection instance = new MySQLConnection();  //Singletone
    private static Connection connection;

    private String url = "jdbc:mysql://localhost:3306/troubleshooting";

    private String login = "root";

    private String pass = "admin";

    private static boolean connected = false;
    private MySQLConnection() {}  //Singletone

    public static MySQLConnection getInstance(){    //Singletone
        return instance;
    }

    private boolean DBconnect(){
        try {
            JaxbParser parser = new JaxbParser();
            File dir1 = new File("D://SomeDir");
            File file = new File(dir1, "config.xml");
            MySQLConnection connection = (MySQLConnection)parser.getObject(file,MySQLConnection.class);
            establishConnection();
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            //
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void MySQLConnectionSetProp(String url, String login, String pass) {
        this.url = url;
        this.login = login;
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }
    //@XmlElement
    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogin() {
        return login;
    }
    //@XmlElement
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }
    //@XmlElement
    public void setPass(String pass) {
        this.pass = pass;
    }

    public Connection getConnection() {
        return connection;
    }
    @XmlTransient
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
