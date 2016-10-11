package sample.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.FX.EditDialog;
import sample.FX.ITreePattern;
import sample.FX.InfoPane;
import sample.FX.Trees.MFITree;
import sample.Utils.Logger.LoggerHelper;
import sample.model.*;
import sample.model.Mothers.dialogableModelDB;

import java.util.logging.Logger;

/**
 * Created by VAN on 14.09.2016.
 */

/**
 @startuml
 : **create Manufacturers Tree**(rootItem, ManufacturersList, Instrument);
 repeat
 :TreeItem **buildTreeItem**(root, model);
 if (Instrument != null?) then (yes)
 if (Instrument.getManufacturer == Manufacturer?) then (yes)
 :TempTreeItem = current;
 endif
 endif
 repeat while (next manufacturer?)
 : return TreeItem;
 stop
 @enduml
 */

/**
 @startuml
 : **create Manufacturers Tree**(rootItem, Instrument);
 :getAllManufacturers;
 repeat
 :TreeItem **buildTreeItem**(root, model);
 if (Instrument != null?) then (yes)
 if (Instrument.getManufacturer == Manufacturer?) then (yes)
 :TempTreeItem = current;
 endif
 endif
 repeat while (next manufacturer?)
 : return TreeItem;
 stop
 @enduml
 */

/*
@startuml
start
 :**startup**;
 if(manufacturer main) then (yes)
 :create Manufacturers Tree(rootTI,null);
 else
 : create FunctionsTree(rootTI,null);
 endif
 stop
@enduml
* */

    /*
@startuml
start
 :**info**;
 :buildInfoPane(TreeItem);
 if(TreeItem have childrens? or is instruments?) then(yes)
 stop
 else
 if(manufacturer main) then (yes)
 if(sel Manufacturer?) then (yes)
 :**getFilteredFunctions**(Manufacturer);
 :**createTree**(selectedTI, list);
 else if(sel Functions?) then(yes)
 :**getFilteredInstuments**(Manufacturer, Functions);
 :**createTree**(selectedTI, list);
 endif
 else
 if(sel Manufacturer?) then (yes)
 :**getFilteredInstuments**(Manufacturer, Functions);
 :**creatTree**(selectedTI,, list);
 else if(sel Functions?) then(yes)
 :**getFilteredManufacturer**(Functions);
 :**createTree**(selectedTI, list);
 endif
 endif
 endif
 stop
@enduml
* */

/*
    @startuml
        start
        :**updateTreeItem**;
        :removeChildrens;
        :temp = treeItem.getValue;
        if(manufacturer main) then (yes)
 if(is Manufacturer?) then (yes)
 :**getFilteredFunctions**(Manufacturer);
 :**createTree**(selectedTI, list);
 else if(is Functions?) then(yes)
 :**getFilteredInstuments**(Manufacturer, Functions);
 :**createTree**(selectedTI, list);
 endif
 else
 if(is Manufacturer?) then (yes)
 :**getFilteredInstuments**(Manufacturer, Functions);
 :**creatTree**(selectedTI,, list);
 else if(is Functions?) then(yes)
 :**getFilteredManufacturer**(Functions);
 :**createTree**(selectedTI, list);
 endif
 endif
        stop
    @enduml
*/

public class InctrumentsController/* implements ITableController*/{
    Logger log = LoggerHelper.getMainControllerLogger();
    private boolean mainManufacturerList = true;
    private TreeItem<? extends dialogableModelDB> instrumentTreeItem ;
    private ITreePattern tree;

    @FXML TreeTableView<dialogableModelDB> InstrumentsTreeTableView;
    @FXML
    public void initialize() {
        tree = new MFITree("Приборы");
        tree.buildMainTree();
        instrumentTreeItem = tree.getRoot();
        instrumentsTree();
        InstrumentsTreeTableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> instrumentInfo(newValue));
    }

    private void instrumentsTree(){
        TreeTableColumn<dialogableModelDB, String> funcColumn =
                new TreeTableColumn<>("Функция");
        funcColumn.setPrefWidth(150);
        funcColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) -> {
                    Object o = param.getValue().getValue();
                    if(o instanceof Instrument) {
                        return new ReadOnlyStringWrapper(((Instrument)o).getFunction().getName());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> typeColumn =
                new TreeTableColumn<>("Тип");
        typeColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) -> {
                    Object o = param.getValue().getValue();
                    if(o instanceof Instrument) {
                        return new ReadOnlyStringWrapper(((Instrument)o).getName());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> orderNumColumn =
                new TreeTableColumn<>("Заказной номер");
        orderNumColumn.setPrefWidth(150);
        orderNumColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Instrument) {
                        return new ReadOnlyStringWrapper(((Instrument)o).getOrderNumber());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> parametersColumn =
                new TreeTableColumn<>("Параметры");
        parametersColumn.setPrefWidth(200);

        parametersColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Instrument) {
                        return new ReadOnlyStringWrapper(((Instrument)o).getParameters());
                    }
                    return null;
                }
        );

        TreeTableColumn< dialogableModelDB, String> noteColumn =
                new TreeTableColumn<>("Примечание");
        noteColumn.setPrefWidth(200);
        noteColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Instrument) {
                        return new ReadOnlyStringWrapper(((Instrument)o).getNote());
                    }
                    return null;
                }
        );


        //zoneTreeTableView = new TreeTableView<>(zoneTreeItem, depIcon);
        //zoneTreeTableView.getColumns().add(zoneTreeTableColumnZone);
        TreeTableColumn<dialogableModelDB, String> instrumentsTreeTableColumn = new TreeTableColumn<>("Производитель");

        instrumentsTreeTableColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<dialogableModelDB, String> p) ->{
            Object o = p.getValue().getValue();
            if(o instanceof Instrument) {
                return new ReadOnlyStringWrapper(((Instrument)o).getName());
            }else if(o instanceof Manufacturer){
                return new ReadOnlyStringWrapper(((Manufacturer)o).getName());
            }else if(o instanceof InstrumentFunc){
                return new ReadOnlyStringWrapper(((InstrumentFunc)o).getName());
            }
            return null;
        });
        instrumentsTreeTableColumn.setPrefWidth(300);

        InstrumentsTreeTableView.setTreeColumn(instrumentsTreeTableColumn);
        InstrumentsTreeTableView.getColumns().setAll(instrumentsTreeTableColumn,typeColumn, orderNumColumn, parametersColumn, noteColumn);

        instrumentTreeItem = new TreeItem<dialogableModelDB>(new Instrument("Приборы", "",null,null)); //Here must be certainly object Zone
        InstrumentsTreeTableView.setRoot(tree.getRoot());
    }
    private void instrumentInfo(TreeItem item){
        tree.info(item);
    }

    public void manufactureInsert_Tap(ActionEvent actionEvent) {
    }
    public void manufactureUpdate_Tap(ActionEvent actionEvent) {
    }
    public void manufactureDelete_Tap(ActionEvent actionEvent) {
    }
    public void instrumentFuncInsert_Tap(ActionEvent actionEvent) {
    }
    public void instrumentFuncUpdate_Tap(ActionEvent actionEvent) {
    }
    public void instrumentFuncDelete_Tap(ActionEvent actionEvent) {
    }
    public void But_Instrument_Insert_Tap(ActionEvent actionEvent) {
    }
    public void But_Instrument_Update_Tap(ActionEvent actionEvent) {
    }
    public void But_Instrument_Del_Tap(ActionEvent actionEvent) {

    }
}
