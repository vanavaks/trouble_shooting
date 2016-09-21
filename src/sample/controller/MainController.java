package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Utils.DB_Provider.*;
import sample.Utils.Logger.FXHandler;
import sample.Utils.Logger.LoggerHelper;


import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * Created by VAN on 14.09.2016.
 */
public class MainController {

    private static Logger log;

    @FXML AnchorPane Pane_trouble;
    @FXML AnchorPane Pane_Equipment;
    @FXML AnchorPane Pane_Instrument;
    @FXML AnchorPane InfoPane;
    @FXML AnchorPane PaneInitiator;
    @FXML AnchorPane Pane_engineer;
    @FXML AnchorPane Pane_PLC;

    @FXML
    public void initialize() {
        log = Logger.getLogger(MainController.class.getName());
        LoggerHelper.configMainControllerLogger(log);
        //loggerInit();
        DBconnect();

        loadAnchorPane(Pane_trouble, "View/troublePane.fxml");
        loadAnchorPane(Pane_Equipment, "View/equipment.fxml");
        loadAnchorPane(Pane_Instrument, "View/instrument.fxml");
        loadAnchorPane(PaneInitiator, "View/Iniciator.fxml");
        loadAnchorPane(Pane_engineer, "View/engineer.fxml");
        loadAnchorPane(Pane_PLC, "View/PLC.fxml");
    }

    private void loggerInit(){
        log = Logger.getLogger(MainController.class.getName());
        try {
            String dirName = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();         //directory trouble_shooting
            String fileName = "Config/logger.cfg";
            boolean confFile = true;
            Handler fxHandler = new FXHandler();
            log.addHandler(fxHandler);
            fxHandler.setLevel(Level.INFO);

            if(confFile){
                log.fine("initializing - trying to load configuration file from \r\n " + dirName + fileName + " ...");
                File file = new File(new File(dirName),fileName);
                InputStream configFile = new FileInputStream(file);

                LogManager.getLogManager().readConfiguration(configFile);
            }
            else {
                //file handler config
                SimpleDateFormat format = new SimpleDateFormat("M-d_HHmmss");

                FileHandler fh = new FileHandler("logger/log"
                        + /*format.format(Calendar.getInstance().getTime()) + */".log",1000000,1);  //"C:/temp/test/MyLogFile_"
                fh.setLevel(Level.ALL);
                fh.setFormatter(new SimpleFormatter());
                log.addHandler( fh );
                //console handler config
                Handler ch = new ConsoleHandler();
                log.addHandler( ch );

                log.setLevel( Level.ALL );

                log.setUseParentHandlers( false );
            }
            log.addHandler(fxHandler);
            //parameters from config file
            //LogManager.getLogManager().readConfiguration(MainController.class.getResourceAsStream("C://config.txt"));
        } catch (SecurityException e) {
            log.log(Level.SEVERE, "Creating log.log file security exaption", e);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Creating log.log file IOexaption", e);
        }
    }
    private void DBconnect(){
        try {
            DBConnProp prop = new DBParametersReaderXML().read();
            IDBConnection connection = MySQL_Connection.getInstance();
            connection.setConnectionProp(prop);
            connection.establishConnection();
            if(connection.isConnected()) log.info("MySQL DataBace is connected");
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
