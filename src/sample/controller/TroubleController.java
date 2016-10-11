package sample.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import sample.FX.EditDialog;
import sample.FX.FXHelper;
import sample.FX.InfoPane;
import sample.Utils.Logger.LoggerHelper;
import sample.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Logger;

/**
 * Created by VAN on 14.09.2016.
 */
public class TroubleController implements ITableController{

    private static Logger log = LoggerHelper.getMainControllerLogger();
    private ObservableList troubleForcesList;
    private ObservableList<Trouble> troubleList;

    @FXML TableView<Trouble> troubleTable;
    @FXML TableColumn<Trouble,Equipment> troubleTable_Equipment;
    @FXML TableColumn<Trouble,String> troubleTable_Data;
    @FXML TableColumn<Trouble,String> troubleTable_Note;
    @FXML TableColumn<Trouble,String> troubleTable_Reason;
    @FXML TableColumn<Trouble,String> troubleTable_Actions;
    @FXML TableColumn<Trouble,Boolean> troubleTable_PPR;
    @FXML TableColumn<Trouble,Engineer> troubleTable_Eng;

    @FXML TableView<Force> TroubleForseTable;
    @FXML TableColumn<Force,Engineer>  TroubleForcEngName;
    @FXML TableColumn<Force,Iniciator>  TroubleForcIniciatorName;
    @FXML TableColumn<Force,Date>    TroubleForcDate;
    @FXML TableColumn<Force,PLC>  TroubleForcPLC;
    @FXML TableColumn<Force,String>  TroubleForcAdress;
    @FXML TableColumn<Force,String>  TroubleForcNote;

    @FXML void troubleAdd_Tap(){}
    @FXML void troubleUpdate_Tap(){}
    @FXML void troubleDelete_Tap(){}
    @FXML void MI_ForceAdd_Tap(){}
    @FXML void MI_TroubleForceEdit_Tap(){}

    @FXML
    public void initialize() {
        troubleTable_Equipment.setCellValueFactory(new PropertyValueFactory<Trouble,Equipment>("equipment"));
        troubleTable_Data.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Trouble, String>, ObservableValue<String>>() {
                      @Override
                      public ObservableValue<String> call(TableColumn.CellDataFeatures<Trouble, String> param) {
                          SimpleStringProperty property = new SimpleStringProperty();
                          DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                          property.setValue(dateFormat.format(param.getValue().getDate()));
                          return property;
                      }
                  }
        );    //new PropertyValueFactory<Trouble,Date>("date")
        troubleTable_Note.setCellValueFactory(new PropertyValueFactory<Trouble,String>("note"));
        troubleTable_Reason.setCellValueFactory(new PropertyValueFactory<Trouble,String>("reason"));
        troubleTable_Actions.setCellValueFactory(new PropertyValueFactory<Trouble,String>("actions"));
        troubleTable_PPR.setCellValueFactory(new PropertyValueFactory<Trouble,Boolean>("ppr"));
        troubleTable_Eng.setCellValueFactory(new PropertyValueFactory("engineer"));





        TroubleForcEngName.setCellValueFactory(new PropertyValueFactory<Force,Engineer>("engineer"));
        //TroubleForcDate.setCellValueFactory(new PropertyValueFactory<Force,Date>("date"));
        TroubleForcPLC.setCellValueFactory(new PropertyValueFactory<Force,PLC>("plc"));
        TroubleForcAdress.setCellValueFactory(new PropertyValueFactory<Force,String>("adress"));
        TroubleForcNote.setCellValueFactory(new PropertyValueFactory<Force,String>("note"));
        TroubleForcIniciatorName.setCellValueFactory(new PropertyValueFactory<Force,Iniciator>("iniciator"));

        troubleTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> troubleShowDetails(newValue));
        TroubleForseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> troubleForseShowDetails(newValue));
        updateList();
    }


    @Override
    public void updateList(){
        troubleList = new Trouble().getData();
        troubleTable.setItems(troubleList);
    }

/*
    @Override
    public ITableController getController() {
        return this;
    }
*/

    private void troubleForseShowDetails(Force force){
        if(force == null) { return;}
        InfoPane.getInstance().rebuild(force,this);
    }

    private void troubleShowDetails(Trouble trouble) {
        trouble = troubleTable.getSelectionModel().getSelectedItem();
        if (trouble == null) return;
        troubleForcesList = Force.getDataFiltered(trouble);
        TroubleForseTable.setItems(troubleForcesList);
        Button addForce = new Button("Добавить форс");
        addForce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                log.fine("Trying to add troube forse");
                Trouble t = troubleTable.getSelectionModel().getSelectedItem();

                if(t != null) {
                    Force f = new Force();
                    f.setTrouble(t);
                    EditDialog E = new EditDialog(f);
                    if (E.getRet()) {
                        f.insert();
                        updateList();
                    }
                }
                else{
                    log.warning("Выберите " + "строку ");
                }
            }
        });
        ObservableList<Node> nlist = FXCollections.observableArrayList();
        nlist.addAll(addForce);
        InfoPane.getInstance().rebuild(trouble,this,nlist);
    }

}
