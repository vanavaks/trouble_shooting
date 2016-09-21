package sample.Utils.Logger;

import sample.Main;
import sample.controller.MainController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.logging.*;

/**
 * Created by VAN on 21.09.2016.
 */
public class LoggerHelper {
    public static Logger getMainControllerLogger(){
        return Logger.getLogger(MainController.class.getName());
    }

    public static Logger configMainControllerLogger(Logger log){

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
        return log;
    }
}
