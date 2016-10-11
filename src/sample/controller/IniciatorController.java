package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.FX.InfoPane;
import sample.model.Iniciator;

/**
 * Created by VAN on 14.09.2016.
 */
public class IniciatorController implements ITableController{
    private ObservableList<Iniciator> iniciatorList;

    @FXML TableView<Iniciator> iniciatorTableView;
    @FXML TableColumn<Iniciator,String> iniciatorTableName;
    @FXML TableColumn<Iniciator,String> iniciatorTableNote;

    @FXML public void initialize(){
        iniciatorTableName.setCellValueFactory(new PropertyValueFactory<Iniciator,String>("Name"));
        iniciatorTableNote.setCellValueFactory(new PropertyValueFactory<Iniciator,String>("Note"));

        updateList();
        iniciatorTableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> iniciatorShowDetails(newValue));
    }

    private void iniciatorShowDetails(Iniciator iniciator){
        if(iniciator != null) {
            InfoPane.getInstance().rebuild(iniciator,this);
        }
    }
    public void updateList(){
        Iniciator iniciator = new Iniciator();
        iniciatorList = iniciator.getData();
        iniciatorTableView.setItems(iniciatorList);
    }

}
