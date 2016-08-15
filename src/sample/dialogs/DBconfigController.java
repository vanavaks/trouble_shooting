package sample.dialogs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.MySQLConnection;
import sample.XML_parser.JaxbParser;
import sample.model.SubZone;
import sample.model.Zone;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.sql.Connection;

/**
 * Created by Иван on 12.08.2016.
 */

public class DBconfigController {
    public MySQLConnection getConnection() {
        return connection;
    }

    public void setConnection(MySQLConnection connection) {
        this.connection = connection;
    }

    private Stage dialogStage;
    MySQLConnection connection;

    JaxbParser parser = new JaxbParser();
//    File file = new File("_config.xml");

    File dir1 = new File("D://SomeDir");
    File file = new File(dir1, "config.xml");

    @FXML
    private void initialize() {
        try {

            connection = (MySQLConnection)parser.getObject(file,MySQLConnection.class);
        } catch (JAXBException e) {
            e.printStackTrace();
            connection = new MySQLConnection();
        }
        TF_login.setText(connection.getLogin());
        TF_pass.setText(connection.getPass());
        TA_connString.setText(connection.getUrl());
    }

    @FXML TextField TF_login;
    @FXML TextField TF_pass;
    @FXML TextArea TA_connString;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML void But_cansel_Tap(){
        dialogStage.close();
    }
    @FXML void But_OK_Tap(){
        String s;
        s = TF_login.getText();
        if(s == "") {AlertInfo("Введите логин базы данных"); return;}
        connection.setLogin(s);
        s = TF_pass.getText();
        if(s == "") {AlertInfo("Введите пароль базы данных"); return;}
        connection.setPass(s);
        s = TA_connString.getText();
        if(s == "") {AlertInfo("Введите строку подключения базы данных"); return;}
        connection.setUrl(s);
        try {
            parser.saveObject(file,connection);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        dialogStage.close();
    }
    private void AlertInfo(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
