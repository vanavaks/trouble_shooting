package sample.Temp;

import javafx.scene.control.Alert;
import sample.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * Created by VAN on 18.08.2016.
 */
public class LoggerFX implements ILogger{

    private Logger logger;
    private final static String dirName = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();         //directory trouble_shooting
    private final static String fileName = "Config/logger.cfg";
    private final boolean configFile = true;
    public LoggerFX(String name) {
        this.logger = Logger.getLogger("Main");
        init();
    }

    public Logger getLogger() {
        return logger;
    }

    private void init(){
        try {
            if(configFile){
                logger.info("initializing - trying to load configuration file from \r\n " + dirName + fileName + " ...");
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
                logger.addHandler( fh );

                //console handler config
                Handler ch = new ConsoleHandler();
                logger.addHandler( ch );

                logger.setLevel( Level.ALL );

                logger.setUseParentHandlers( false );
            }
            //parameters from config file
            //LogManager.getLogManager().readConfiguration(MainController.class.getResourceAsStream("C://config.txt"));
        } catch (SecurityException e) {
            exception("Creating log.log file security exaption", e);
        } catch (IOException e) {
            exception("Creating log.log file IOexaption", e);
        }
    }

    @Override
    public void info(String s){
        logger.info(s);
    }

    @Override
    public void warning(String s){
        logger.warning(s);
        AlertWarning(s);
    }

    @Override
    public void error(String s){
        logger.severe(s);
        AlertError(s);
    }

    @Override
    public void exception(String s, Exception e){
        logger.log(Level.SEVERE, s, e);
        AlertError(s);
    }

    private void AlertWin(Alert.AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void AlertWarning(String s){
        AlertWin(Alert.AlertType.WARNING,"Warning",s);
    }
    private void AlertError(String s){
        AlertWin(Alert.AlertType.ERROR,"Error",s);
    }
    private void AlertInfo(String s){
        AlertWin(Alert.AlertType.INFORMATION,"Information",s);
    }

}
