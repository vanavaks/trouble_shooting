package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.FX.InfoPane;
import sample.model.PLC;

/**
 * Created by VAN on 14.09.2016.
 */
public class PLCController implements ITableController{
    ObservableList<PLC> plcList;
    @FXML TableView<PLC> plcTable;
    @FXML TableColumn<PLC, String> plcTableName;
    @FXML TableColumn<PLC, String> plcTablePass;

    @FXML public void initialize(){
        plcTableName.setCellValueFactory(new PropertyValueFactory<PLC,String>("name"));
        plcTablePass.setCellValueFactory(new PropertyValueFactory<PLC,String>("password"));
        updateList();

        plcTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> plcShowDetails(newValue));
    }

    private void plcShowDetails(PLC plc){
        if(plc != null){
            InfoPane.getInstance().rebuild(plc,this);
        }
    }
    public void updateList(){
        PLC plc = new PLC();
        plcList = plc.getData();
        //CombPLC.setItems(plcList);
        plcTable.setItems(plcList);
        //CombPLC_Filter.setItems(plcList);

    }
}
