package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.model.Engineer;
import sample.FX.InfoPane;

/**
 * Created by VAN on 14.09.2016.
 */
public class EngineerController implements  ITableController{
    private ObservableList<Engineer> engList ;
    @FXML
    TableView<Engineer> EngTable;

    @FXML
    TableColumn<Engineer,Integer> EngFnameColumn;
    @FXML TableColumn<Engineer,String>  EngLnameColumn;
    @FXML TableColumn<Engineer,String>  EngShiftColumn;

    @FXML public void initialize() {
        EngFnameColumn.setCellValueFactory(new PropertyValueFactory<Engineer,Integer>("firstName"));
        EngLnameColumn.setCellValueFactory(new PropertyValueFactory<Engineer, String>("lastName"));
        EngShiftColumn.setCellValueFactory(new PropertyValueFactory<Engineer, String>("shift"));

        updateList();
        EngTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> engineerShowDetail(newValue));
    }

    private void engineerShowDetail(Engineer eng){
        if(eng != null){
            InfoPane.getInstance().rebuild(eng,this);
        }
    }
    public void updateList(){
        Engineer e = new Engineer();
        engList = e.getData();
        EngTable.setItems(engList);
    }

}
