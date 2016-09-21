package sample.Utils.Logger;

import javafx.scene.control.Alert;

import java.io.PrintWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created by VAN on 21.09.2016.
 */
public class FXHandler extends Handler {
    PrintWriter printWriter;

    public FXHandler() {
        super();
    }

    public void publish(LogRecord record) {
        // ensure that this log record should be logged by this Handler
        if (!isLoggable(record))
            return;

        Level level = record.getLevel();
        if(level == Level.SEVERE) {
            AlertError(record.getMessage());
        }
        else if(level == Level.WARNING) {
            AlertWarning(record.getMessage());
        }
        else if(level == Level.INFO) {
            AlertInfo(record.getMessage());
        }
    }

    public void flush() {
    }

    public void close() throws SecurityException {
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
