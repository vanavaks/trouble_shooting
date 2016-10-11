package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Utils.DB_Provider.*;
import sample.Utils.Logger.LoggerHelper;
import sample.FX.InfoPane;
import sample.model.Mothers.dialogableModelDB;


import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.logging.*;

/**
 * Created by VAN on 14.09.2016.
 */
public class MainController {

    private static Logger log;

    @FXML AnchorPane Pane_trouble;
    @FXML AnchorPane Pane_Equipment;
    @FXML AnchorPane Pane_Instrument;
    @FXML AnchorPane infoPane;
    @FXML AnchorPane PaneInitiator;
    @FXML AnchorPane Pane_engineer;
    @FXML AnchorPane Pane_PLC;


    @FXML
    public void initialize() {
        log = Logger.getLogger(MainController.class.getName());
        LoggerHelper.configMainControllerLogger(log);

        DBconnect();

        loadAnchorPane(Pane_trouble, "View/troublePane.fxml");
        loadAnchorPane(Pane_Equipment, "View/equipment.fxml");
        loadAnchorPane(Pane_Instrument, "View/instrument.fxml");
        loadAnchorPane(PaneInitiator, "View/Iniciator.fxml");
        loadAnchorPane(Pane_engineer, "View/engineer.fxml");
        loadAnchorPane(Pane_PLC, "View/PLC.fxml");

        infoPane.getChildren().add(InfoPane.getInstance());
    }
    private void DBconnect(){
        try {
            DBConnProp prop = new DBParametersReaderXML().read();
            IDBConnection connection = MySQL_Connection.getInstance();
            connection.setConnectionProp(prop);
            connection.establishConnection();
            if(connection.isConnected()) {
                dialogableModelDB.setConnection(connection.getConnection());
                log.info("MySQL DataBace is connected");
            }
            else log.warning("MySQL DataBase is don't connected");
        } catch (JAXBException e) {
            log.log(Level.SEVERE, "JAXB Exception, Не найден или не правелен файл настроек подключения к базе данных", e);
        } catch(SQLException e){
            log.log(Level.SEVERE, "SQL Exception, Невозможно подключиться к базе данных", e);
        } catch (Exception e){
            log.log(Level.SEVERE, "Exception, Неизвесное исключение при подключении к базе данных", e);
        }
    }

    private void loadAnchorPane(AnchorPane anchorPane, String FXMLFile)
    {
        AnchorPane page = Main.loadPane(FXMLFile);
        anchorPane.getChildren().clear();///name of pane where you want to put the fxml.
        anchorPane.getChildren().add(page);
    }

    public void But_SQL_config_Tap(ActionEvent actionEvent) {
        Main.showSQLConfig_dialog();
    }
}
