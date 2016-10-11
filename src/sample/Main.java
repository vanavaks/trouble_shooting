package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Temp.dialogs.PLCEdit_Controller;
import sample.Temp.dialogs.SubZoneEditController;
import sample.Temp.dialogs.ZoneAdd_Controller;
import sample.controller.DBconfigController;
import sample.model.PLC;
import sample.model.SubZone;
import sample.model.Zone;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/MainForm.fxml"));  //sample.fxml
        primaryStage.setTitle("Trouble Shooting");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static AnchorPane loadPane(String url){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(url));
            //GridPane page = (GridPane)loader.load();
            AnchorPane page = (AnchorPane) loader.load();
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showZoneEditDialog() {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("dialogs/ZoneEditDialog.FXML"));
            //GridPane page = (GridPane)loader.load();
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование под зоны");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            ZoneAdd_Controller controller = loader.getController();
            controller.setDialogStage(dialogStage);
//            controller.setPerson(person);

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPLCEditDialog(PLC plc) {
        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("dialogs/PLC_EditDialog.FXML"));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование PLC");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            PLCEdit_Controller controller = loader.getController();
            controller.setPlc(plc);
            controller.setDialogStage(dialogStage);


            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSubZoneEditDialog(SubZone subZone, ObservableList<Zone> zoneList) {
        try {

            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("dialogs/SubZoneEditDialog.FXML"));
            //GridPane page = (GridPane)loader.load();
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование Подзоны");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            SubZoneEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSub_Zone(subZone);
            controller.setZoneList(zoneList);
            controller.show_data();

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            AlertInfo("Dialog Box, SQL Editor exaption");
        }
    }


    public static void showSQLConfig_dialog() {


        try {
            /*Parent addwin = FXMLLoader.load(Main.class.getResource("DB_configDialog.fxml"));
            primaryStage.setTitle("Добавить БД");
            primaryStage.setScene(new Scene(addwin, 400, 250));
            primaryStage.setResizable(false);
            primaryStage.toFront();
            primaryStage.show();
*/
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(Main.class.getResource("View/DB_configDialog.fxml"));
            //GridPane page = (GridPane)loader.load();
            GridPane page = loader.load();
            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Конфигурация MySQL подключения");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Передаём адресата в контроллер.

            DBconfigController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            AlertInfo("Dialog box SQL EDitor Exeption");
        }
    }
    private static void AlertInfo(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}
   /* static void showModeledDialog(){
        String FXML = "dialogs/SubZoneEditDialog.FXML";
        String title = "Редактирование Подзоны";

        try {
            // Загружаем fxml-файл и создаём новую сцену
            // для всплывающего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(FXML));
            AnchorPane page = (AnchorPane) loader.load();

            // Создаём диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаём адресата в контроллер.
            SubZoneEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSub_Zone(subZone);
            controller.setZoneList(zoneList);
            controller.show_data();

            // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}*/

/*
class Controler implements tabList{

}
interface tabList {
    boolean Add(Object item);
    ObservableList<Object> Get();
    boolean Del(Object item);
    boolean Update(Object item);

    void updateList();
}*/
