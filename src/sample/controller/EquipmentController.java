package sample.controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.FX.EditDialog;
import sample.FX.InfoPane;
import sample.model.*;
import sample.model.Mothers.dialogableModelDB;

/**
 * Created by VAN on 14.09.2016.
 */
public class EquipmentController implements ITableController{
    private ObservableList<Zone> zoneList;

    @FXML TreeTableView<dialogableModelDB> zoneTreeTableView;

    TreeItem<dialogableModelDB> zoneTreeItem1;
    @FXML public void initialize(){
        updateList();
    }

    @Override
    public void updateList() {
        zoneUpdateList();
        zoneTreeUpdate();
        zoneTree1();
        zoneTreeTableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> equipmentInfo(newValue));
    }

    private void equipmentInfo(TreeItem<dialogableModelDB> i){
        if(i == null) return;
        if(i.getValue() == null) return;


        if(i.getValue() instanceof SubZone){
            updateSubZoneEquipments(i);
            Button NewEqBut = new Button("Добавить оборудование");
            NewEqBut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    EditDialog<Equipment> ED= new EditDialog(new Equipment((SubZone)i.getValue()));
                    if(ED.getRet()) {
                        ED.getModel().insert();
                        updateSubZoneEquipments(i);
                    }
                }
            });
            InfoPane.getInstance().rebuild(i.getValue(),this, NewEqBut);
        }else if(i.getValue() instanceof Zone) {
            Button NewSZBut = new Button("Добавить Подзону");
            NewSZBut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    EditDialog<SubZone> ED = new EditDialog(new SubZone((Zone)i.getValue()));
                    if (ED.getRet()) {
                        ED.getModel().insert();
                        updateZoneEquipments(i);
                    }
                }
            });
            InfoPane.getInstance().rebuild(i.getValue(), this, NewSZBut);
        }else{
            InfoPane.getInstance().rebuild(i.getValue(), this);
        }
    }

    private void updateZoneEquipments(TreeItem<dialogableModelDB> i) {
        i.getChildren().clear();
        ObservableList<SubZone> szList = SubZone.getDataFiltered((Zone)i.getValue());
        if(szList != null){
            for(SubZone sz: szList){
                TreeItem<dialogableModelDB> szt = new TreeItem<>(sz);
                i.getChildren().add(szt);
                szt.setExpanded(true);
                updateSubZoneEquipments(szt);
            }
        }
    }

    dialogableModelDB selectedEq;

    private void updateSubZoneEquipments(TreeItem i){
        Object o = i.getValue();
        ObservableList<Equipment> eqList = null;
        i.getChildren().clear();
        eqList = Equipment.getDataFiltered((SubZone) o,null);
        for(Equipment e: eqList){
            TreeItem<dialogableModelDB> t = new TreeItem<>(e);
            i.getChildren().add(t);
        }
    }

//    private void EquipmentUpdateList(){
//        equipmentList = new Equipment().getData();
//
//    }
//    private void subzoneUpdateList(){
//        subZoneList = new SubZone().getData();
//    }
    private void zoneUpdateList(){
        Zone z = new Zone();
        zoneList = z.getData();
    }
    public void zoneTreeUpdate(){
//        zoneTreeItem1 = new TreeItem<>(new Equipment("Factory"," ",new SubZone())); //Here must be certainly object Zone
//
//        for(Zone z: zoneList){
//            TreeItem<dialogableModelDB> t = new TreeItem<>(new Equipment(z.getName(), z.getPosition(), new SubZone(z),z.getNote()));
//            for(SubZone sz: subZoneList){
//                if(sz.getZone().getId() == z.getId()){
//                    TreeItem<dialogableModelDB> szt = new TreeItem<>(new Equipment(sz.getName(), sz.getPosition(), sz, sz.getNote()));
//                    for(Equipment eq :equipmentList){
//                        if(eq.getSubZone().getId() == sz.getId()){
//                            TreeItem<dialogableModelDB> eqt = new TreeItem<>(eq);
//                            szt.getChildren().add(eqt);
//                        }
//                    }
//                    t.getChildren().add(szt);
//                }
//                t.setExpanded(true);
//            }
//            zoneTreeItem1.getChildren().add(t);
//            t.setExpanded(true);
//        }

        zoneTreeItem1 = new TreeItem<>(new Equipment("Factory"," ",new SubZone())); //Here must be certainly object Zone
        zoneTreeItem1.setExpanded(true);
        for(Zone z: zoneList){
            TreeItem<dialogableModelDB> t = new TreeItem<>(z);
            ObservableList<SubZone> szList = SubZone.getDataFiltered(z);
            if(szList != null){
                szList.stream().forEach(sz -> {
                    TreeItem<dialogableModelDB> szt = new TreeItem<>(sz);
                    t.getChildren().add(szt);
                    szt.setExpanded(true);
                });

                for(SubZone sz: szList){
                    TreeItem<dialogableModelDB> szt = new TreeItem<>(sz);
                    t.getChildren().add(szt);
                    szt.setExpanded(true);
                }
            }
            zoneTreeItem1.getChildren().add(t);
            t.setExpanded(true);
        }
        zoneTreeTableView.setRoot(zoneTreeItem1);
    }

    private void zoneTree1(){
//        TreeTableColumn<dialogableModelDB, String> nameColumn =
//                new TreeTableColumn<>("Название");
//
//        nameColumn.setPrefWidth(150);
//        nameColumn.setCellValueFactory(
//                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->
//                {
//                    Object o = param.getValue().getValue();
//                    if(o instanceof Equipment){
//                        return new ReadOnlyStringWrapper(((Equipment)o).getPosition()+ " " + ((Equipment)o).getName());
//                    }
//                    else if(o instanceof SubZone){
//                        return new ReadOnlyStringWrapper(((SubZone)o).getPosition()+ " " + ((SubZone)o).getName());
//                    }
//                    else if(o instanceof Zone){
//                        return new ReadOnlyStringWrapper(((Zone)o).getPosition()+ " " + ((Zone)o).getName());
//                    }
//                    return null;
//                }
//        );

        TreeTableColumn<dialogableModelDB, String> instrumentColumn =
                new TreeTableColumn<>("Прибор");
        instrumentColumn.setPrefWidth(150);
        instrumentColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Equipment){
                        return new ReadOnlyStringWrapper(((Equipment)o).getInstrment().toString());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> apointmentColumn =
                new TreeTableColumn<>("Назначение");
        apointmentColumn.setPrefWidth(200);
        apointmentColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Equipment){
                        return new ReadOnlyStringWrapper(((Equipment)o).getAppointment());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> pageColumn =
                new TreeTableColumn<>("Страница");
        pageColumn.setPrefWidth(40);
        pageColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Equipment){
                        return new ReadOnlyStringWrapper(((Equipment)o).getSchematicDiagramPage());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> noteColumn =
                new TreeTableColumn<>("Примечание");
        noteColumn.setPrefWidth(200);
        noteColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Equipment){
                        return new ReadOnlyStringWrapper(((Equipment)o).getNote());
                    }
                    return null;
                }
        );

        TreeTableColumn<dialogableModelDB, String> zoneTreeTableColumnZone = new TreeTableColumn<>("Оборудование");
        zoneTreeTableColumnZone.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<dialogableModelDB, String> param) ->{
                    Object o = param.getValue().getValue();
                    if(o instanceof Equipment){
                        return new ReadOnlyStringWrapper(((Equipment)o).getPosition() +  " " + ((Equipment)o).getName());
                    }
                    else if(o instanceof Zone){
                        return new ReadOnlyStringWrapper(((Zone)o).getPosition() +  " " + ((Zone)o).getName());
                    }
                    else if(o instanceof SubZone){
                        return new ReadOnlyStringWrapper(((SubZone)o).getPosition() +  " " + ((SubZone)o).getName());
                    }
                    return null;
                }
        );



        zoneTreeTableColumnZone.setPrefWidth(300);
        zoneTreeTableView.setRoot(zoneTreeItem1);
        zoneTreeTableView.setTreeColumn(zoneTreeTableColumnZone);
        zoneTreeTableView.getColumns().setAll(zoneTreeTableColumnZone,apointmentColumn, instrumentColumn, pageColumn, noteColumn);
        zoneTreeUpdate();
    }

    public void MI_subzoneAdd_Tap(ActionEvent actionEvent) {
    }
    public void MI_subzoneUpdate_Tap(ActionEvent actionEvent) {
    }
    public void MI_subzoneDel_Tap(ActionEvent actionEvent) {
    }
    public void But_EquipmentInsert_Tap(ActionEvent actionEvent) {
    }
    public void But_EquipmentUpdate_Tap(ActionEvent actionEvent) {
    }
    public void But_EquipmentDelete_Tap(ActionEvent actionEvent) {

    }

    public void But_ZoneAdd_Tap(ActionEvent actionEvent) {
    }
    public void But_ZoneUpdate_Tap(ActionEvent actionEvent) {
    }
    public void But_ZoneDel_Tap(ActionEvent actionEvent) {
    }
}
