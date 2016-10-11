package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Utils.DB_Provider.*;
import sample.Utils.Logger.LoggerHelper;
import sample.controller.MainController;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Иван on 12.08.2016.
 */

public class DBconfigController {
    //private ILogger log;
    Logger log;
    private DBConnProp prop;
    private Stage dialogStage;

    @FXML TextField TF_login;
    @FXML TextField TF_pass;
    @FXML TextArea TA_connString;

    @FXML
    private void initialize() {
        log = LoggerHelper.getMainControllerLogger(); //Logger.getLogger(MainController.class.getName()); //new LoggerFX(DBconfigController.class.getName());

        try {
            DBConnProp prop = new DBParametersReaderXML().read();
            TF_login.setText(prop.getLogin());
            TF_pass.setText(prop.getPass());
            TA_connString.setText(prop.getUrl());
        } catch (JAXBException e) {
            log.log(Level.SEVERE, "JAXB Exception, Не найден или не правелен файл настроек подключения к базе данных", e);
            viewDefSetting();
        } catch (Exception e){
            log.log(Level.SEVERE, "Exception, Неизвесное исключение", e);
            viewDefSetting();
        }

    }

    private void viewDefSetting(){
        TF_login.setText("root");
        TF_pass.setText("admin");
        TA_connString.setText("jdbc:mysql://localhost:3306/troubleshooting");
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML void But_cansel_Tap(){
        dialogStage.close();
    }
    @FXML void But_OK_Tap(){
        if(!Assert()) return;
        IDBParametersReader reader = new DBParametersReaderXML();
        try {
            reader.write(prop);
            log.info("Сохранен файл настроек подключения к базе данных");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Save property Exception",e);
        }
        dialogStage.close();
    }

    private boolean Assert(){
        prop = new DBConnProp(TA_connString.getText(), TF_login.getText(), TF_pass.getText());
        String s = prop.AssertString();
        if(s.equals("")){
            return true;
        }
        log.warning(s);
        return false;
    }

    @FXML void But_reconnect_Tap(){
        try {
            IDBConnection connection = MySQL_Connection.getInstance();
            if(!Assert()){ return;  }
            connection.setConnectionProp(prop);
            connection.establishConnection();
            if(connection.isConnected()) log.info("MySQL DataBace is connected");
            else log.warning("MySQL DataBase is don't connected");
        } catch(SQLException e){
            log.log(Level.SEVERE, "SQL Exception, Невозможно подключиться к базе данных", e);
        } catch (Exception e){
            log.log(Level.SEVERE, "Exception, Неизвесное исключение при подключении к базе данных", e);
        }
    }

}
