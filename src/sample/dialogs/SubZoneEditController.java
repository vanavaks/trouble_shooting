package sample.dialogs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.PLC;
import sample.model.SubZone;
import sample.model.Zone;

/**
 * Created by Иван on 05.07.2016.
 */
public class SubZoneEditController {
    @FXML TextField TF_SubZoneName;
    @FXML TextArea TA_SubZoneNote;
    @FXML ComboBox<Zone> Comb_Zone;

    @FXML Button But_OK;
    @FXML Button But_Cancel;


    private Stage dialogStage;
    private SubZone sub_Zone;
    private ObservableList<Zone> zoneList;
    private int n=0;

    @FXML
    private void initialize() {


    }

    public void show_data(){
        System.out.println("----subZoneController----");
        for(Zone zone :zoneList){
            System.out.println(zone.getName());
        }

        if(sub_Zone != null){
            TF_SubZoneName.setText(sub_Zone.getName());
            TA_SubZoneNote.setText(sub_Zone.getNote());
        }
        if(zoneList != null){
            Comb_Zone.setItems(zoneList);
            for(Zone zone :zoneList){
                System.out.println(zone.getName());
            }

        }
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

    @FXML void But_OK_Tap(){
        sub_Zone.setName(TF_SubZoneName.getText());
        sub_Zone.setNote(TA_SubZoneNote.getText());
        sub_Zone.setZone(Comb_Zone.getValue());
        dialogStage.close();
    }


    public  void setSub_Zone(SubZone sub_Zone) {
        this.sub_Zone = sub_Zone;
    }

    public void setZoneList(ObservableList<Zone> zoneList) {
        this.zoneList = zoneList;
    }

    public int getN() {
        return this.n;
    }

    public static void setN(int n) {
        n = n;
    }
}
