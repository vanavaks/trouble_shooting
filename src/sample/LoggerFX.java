package sample;

import javafx.scene.control.Alert;

/**
 * Created by VAN on 18.08.2016.
 */
public class LoggerFX extends java.util.logging.Logger {

    private static boolean enablePrintln;

    public LoggerFX(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    @Override
    public void info(String s){
        if(enablePrintln) System.out.println(s);
        super.info(s);
    }

    @Override
    public void warning(String s){
        if(enablePrintln) System.out.println(s);
        super.warning(s);
        AlertWarning(s);
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
