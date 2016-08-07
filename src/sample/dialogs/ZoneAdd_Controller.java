package sample.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Иван on 02.07.2016.
 */
public class ZoneAdd_Controller {

    @FXML TextField TF_Position;
    @FXML TextField TF_Note;
    @FXML Button But_Add;
    @FXML Button ButCancel;


    private Stage dialogStage;

    @FXML
    private void initialize() {

    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML void But_Cancel_Tap(){
        dialogStage.close();
    }

    @FXML void But_Add_Tap(){

    }
}
