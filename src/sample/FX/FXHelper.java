package sample.FX;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import sample.Utils.Logger.LoggerHelper;
import sample.model.Mothers.Table;
import sample.model.Mothers.dialogableModelDB;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by VAN on 22.09.2016.
 */
public class FXHelper {
    private static Logger log = LoggerHelper.getMainControllerLogger();

    public static <T extends dialogableModelDB> boolean DeleteDialog(T model){
        if(model == null){
            log.warning("Выберите " + "строку " + "перед удалением");
            return false;
        }

        String modelName = model.getModelName();
        log.fine("Try to delete" + modelName + model);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удалить");

        alert.setHeaderText("Подтвердите удаление " + modelName);
        alert.setContentText(model.toString());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            // ... user chose OK
            try {
                if(model.delete()){
                    log.info(modelName + " " + model + " удален.");
                    return true;
                }
                else{
                    log.warning("невозможно удалить " + modelName + " " + model + ".");
                    return false;
                }
            } catch (SQLException e) {
                log.log(Level.SEVERE,"Deleting " + modelName + " error", e);
            }

            return false;
        } else {
            // ... user chose CANCEL or closed the dialog

        }
        return false;
    }

    public static void AlertInfo(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat getDateFormat() {
        return dateFormat;
    }
    public static void setDateFormat(DateFormat dateFormat) {
        FXHelper.dateFormat = dateFormat;
    }
    public static void setDateFormat(String string){
        FXHelper.dateFormat = new SimpleDateFormat(string);
    }


    public static String dateFormatted(Date date){
        return dateFormat.format(date);
    }
}
