package sample.Temp.dialogs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.PLC;

/**
 * Created by Иван on 04.07.2016.
 */
public class PLCEdit_Controller {

    @FXML TextField TF_Name;
    @FXML TextField TF_Pass;
    @FXML Button But_Add;
    @FXML Button ButCancel;


    private Stage dialogStage;
    private PLC _plc;

    @FXML
    private void initialize() {
       /* String s;
        if(_plc.getName() == ""){
            s = "Введите название контроллера";
        }
        else{
            s = _plc.getName();
        }
        TF_Name.setText(s);*/
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
        _plc.setName(TF_Name.getText());
        _plc.setPassword(TF_Pass.getText());
        dialogStage.close();
    }

    public void setPlc(PLC plc) {
        this._plc = plc;
    }
}