package sample.Utils.DB_Provider;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by VAN on 15.09.2016.
 */
@XmlRootElement //(name = "DataBase")
@XmlType(propOrder = {"url","login","pass"})
public class DBConnProp {
    private String url;
    private String login;
    private String pass;

    private final static int minUrlLength = 10;
    private final static int minLoginLength = 1;
    public String AssertString(){
        String s = "";
        if(!urlAssert()){s += "Проверьте строку подключения. ";}
        if(!loginAssert()){s += "Логин должен быть больше " + minLoginLength + " символа";}
       return s;
    }

    public boolean Assert(){
        if(!urlAssert()){return false;}
        if(!loginAssert()){return false;}
        return true;
    }

    private boolean urlAssert(){
        return this.url.length() > minUrlLength;
    }

    private boolean loginAssert(){
        return this.login.length() > minLoginLength;
    }
    public DBConnProp(String url, String login, String pass) {
        this.url = url;
        this.login = login;
        this.pass = pass;
    }
    public DBConnProp() {
        this.url = "jdbc:mysql://localhost:3306/troubleshooting";
        this.login = "root";
        this.pass = "admin";
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getUrl() {

        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
