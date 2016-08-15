package sample;

//import java.awt.*;
import java.io.File;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.stage.FileChooser;
import sample.XML_parser.JaxbParser;
import sample.XML_parser.Jaxb_Aplic;
import sample.model.*;
import javafx.scene.image.*;

import javax.xml.bind.JAXBException;
import java.util.stream.Collectors;

public class Controller {
    private ObservableList<Engineer> engList ;
    private ObservableList<Zone> zoneList;
    private ObservableList<Force> forceList;
    private  ObservableList<PLC> plcList;
    private MySQLConnection connection;
    private ObservableList<Iniciator> iniciatorList;
    private  ObservableList<ImageBlob> imageBlob;
    private ObservableList<SubZone> subZoneList;
    //Engineer Tab
    @FXML TableView <Engineer> EngTable;

    @FXML TableColumn<Engineer,Integer> EngFnameColumn;
    @FXML TableColumn<Engineer,String>  EngLnameColumn;
    @FXML TableColumn<Engineer,String>  EngShiftColumn;

    @FXML Button But_EngAdd;
    @FXML Button But_EngUpdate;
    @FXML Button But_EngDel;

    @FXML TextField TW_EngName;
    @FXML TextField TW_EngShift;
    @FXML TextField TW_EngLastName;


    //Zone Tab
//    @FXML TableView <Zone> ZoneTable;

    //@FXML TableColumn<Zone,Integer> ZoneIdColumn;
//    @FXML TableColumn<Zone,String>  ZoneNameColumn;
//    @FXML TableColumn<Zone,String>  ZonePosColumn;
//    @FXML TableColumn<Zone,String>  ZoneNoteColumn;

//    @FXML TextField TW_Zone_pos;
//    @FXML TextField TW_Zone_name;
//    @FXML TextField TW_Zone_note;
//
//    @FXML Button But_ZoneAdd;
//    @FXML Button But_ZoneDel;
//    @FXML Button But_ZoneUpdate;

    //Forse Tab
    @FXML TableView<Force> ForseTable;

    @FXML TableColumn<Force,Engineer>  ForcEngName;
    @FXML TableColumn<Force,Iniciator>  ForcIniciatorName;
    @FXML TableColumn<Force,Date>    ForcDate;
    @FXML TableColumn<Force,PLC>  ForcPLC;
    @FXML TableColumn<Force,String>  ForcAdress;
    @FXML TableColumn<Force,String>  ForcNote;


    @FXML ComboBox<PLC> CombPLC;
    @FXML ComboBox<Engineer> CombEngineer;
    @FXML ComboBox<Iniciator> CombIniciator;

    @FXML TextField TW_ForseAddr;
    @FXML TextArea TW_ForceNote;
    @FXML DatePicker DatPicker_ForceDate;
    @FXML Button But_ForceAdd;
    @FXML Button But_ForceDel;
    @FXML Button But_ForceUpdate;

    //Forse Filter
    @FXML ComboBox<PLC> CombPLC_Filter;
    @FXML ComboBox<Engineer> CombEngineer_Filter;
    @FXML ComboBox<Iniciator> CombIniciator_Filter;
    @FXML DatePicker DatPicker_Before;
    @FXML DatePicker DatPicker_After;
    @FXML Button But_ForseFilter;
    @FXML Button But_ForseFilterReset;

    //PLC Table
    @FXML TableView<PLC> plcTable;

    @FXML TableColumn<PLC,String> plcTableName;
    @FXML TableColumn<PLC,String> plcTablePass;

    @FXML Button But_PLCAdd;
    @FXML Button But_PLCUpdate;
    @FXML Button But_PLCDel;

    @FXML TextField TW_PLCLabel;
    @FXML TextField TW_PLCPass;
    @FXML MenuItem MI_AddPLC;

    //Iniciator
    @FXML TableView<Iniciator> iniciatorTableView;
    @FXML TableColumn<Iniciator,String> iniciatorTableName;
    @FXML TableColumn<Iniciator,String> iniciatorTableNote;

    @FXML Button But_iniciatorAdd;
    @FXML Button But_iniciatorUpdate;
    @FXML Button But_iniciatorDel;

    @FXML TextField TW_iniciatorName;
    @FXML TextField TW_iniciatorNote;

    //Image
    @FXML Button But_ChooseImageFile;
    @FXML ImageView ImageView1;
    @FXML Button But_imageNext;
    @FXML Button But_imagePrev;
    @FXML Button But_imageAdd;
    @FXML Button But_imageGet;
    @FXML TextField TW_imageName;
    @FXML ChoiceBox<ImageBlob> Comb_images;
    @FXML Button But_getClipboardImage;
    @FXML Button But_ImageDel;

    // Troubles
//    @FXML ContextMenu CMenu_TrblZone;
//    @FXML MenuItem CMenu_TrblZoneAdd;
//    @FXML Button But_newForce;
//    @FXML Button But_TroubleDelete;
//    @FXML Button But_TroubleInsert;
//    @FXML Button But_TroubleUpdate;

    // Subzone
//    @FXML TableView<SubZone> subzoneTableview;
//    @FXML MenuItem MI_subzoneDel;
//    @FXML MenuItem MI_subzoneAdd;
//    @FXML MenuItem MI_subzoneUpdate;
//    @FXML TableColumn<SubZone, String> subzoneTableName;
//    @FXML TableColumn<SubZone, String> subzoneTableZone;
//    @FXML TableColumn<SubZone, String> subzoneTableNote;

    @FXML public void initialize(){
//        try {
//            JaxbParser parser = new JaxbParser();
//            File dir1 = new File("D://SomeDir");
//            File file = new File(dir1, "config.xml");
//            connection = (MySQLConnection)parser.getObject(file,MySQLConnection.class);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//            connection = new MySQLConnection();
//        }
//        try {
//            connection.establishConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            imageInit();
//            engneerInit();
//            forceInit();
//            plcInit();
//            zoneInit();
//            iniciatorInit();
//            subzoneInit();
//            ManufacturerInit();
//            InstrumentFuncInit();
//            InstrumentInit();
//            EquipmentInit();
//            zoneTree();
//            instrumentsTree();
//            troubleInit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        if(DBconnect()){
            DBreadDatas();
        }
    }



    private boolean DBconnect(){
        try {
            JaxbParser parser = new JaxbParser();
            File dir1 = new File("D://SomeDir");
            File file = new File(dir1, "config.xml");
            connection = (MySQLConnection)parser.getObject(file,MySQLConnection.class);
            connection.establishConnection();
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            connection = new MySQLConnection();
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void DBreadDatas(){
        try {
            imageInit();
            engneerInit();
            forceInit();
            plcInit();
            zoneInit();
            iniciatorInit();
            subzoneInit();
            ManufacturerInit();
            InstrumentFuncInit();
            InstrumentInit();
            EquipmentInit();
            zoneTree();
            instrumentsTree();
            troubleInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveSqlConfigXML()throws Exception{
        JaxbParser parser = new JaxbParser();
        File file = new File("config.xml");
        parser.saveObject(file,connection);
    }

    //Manufacturer
    public ObservableList<Manufacturer> manufacturerList;
    @FXML TableView<Manufacturer> manufacturerTable;
    @FXML TableColumn<Manufacturer, String> manufacturerTableName;
    @FXML TableColumn<Manufacturer, String> manufacturerTableNote;
    @FXML MenuItem manufactureDelete;
    @FXML MenuItem manufactureUpdate;
    @FXML MenuItem manufactureInsert;
    private void ManufacturerInit(){
        manufacturerTableName.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("name"));
        manufacturerTableNote.setCellValueFactory(new PropertyValueFactory<Manufacturer,String>("note"));

        ManufacturerUpdateList();
    }
    private void ManufacturerUpdateList(){
        manufacturerList = new Manufacturer().getData();
        manufacturerTable.setItems(manufacturerList);
    }
    @FXML void manufactureDelete_Tap(){
        Manufacturer m = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getManufacturer();
        if(m != null){
            m.delete();
            ManufacturerUpdateList();
            instrumentsTreeUpdate();
        }
    }
    @FXML void manufactureUpdate_Tap(){
        Manufacturer m = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getManufacturer();
        if(m != null){
            EditDialog<Manufacturer> e = new EditDialog<Manufacturer>(m);
            if(e.getRet()) {
                m.update();
                ManufacturerUpdateList();
                instrumentsTreeUpdate();
            }
        }
    }
    @FXML void manufactureInsert_Tap(){
        Manufacturer m = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getManufacturer();
        if (m == null) m = new Manufacturer(0,"Производитель","примечание");
        EditDialog<Manufacturer> e = new EditDialog<Manufacturer>(m);
        if(e.getRet()) {
            m.insert();
            ManufacturerUpdateList();
            instrumentsTreeUpdate();
        }
    }

    //-------------Instrument Function-------------------------------------
    public ObservableList<InstrumentFunc> instrumentFuncList;
    @FXML TableView<InstrumentFunc> instrumentFuncTable;
    @FXML TableColumn<InstrumentFunc, String> instrumentFuncTableName;
    @FXML TableColumn<InstrumentFunc, String> instrumentFuncTableNote;
    @FXML MenuItem functionDelete;
    @FXML MenuItem functionUpdate;
    @FXML MenuItem functionInsert;
    private void InstrumentFuncInit(){
        instrumentFuncTableName.setCellValueFactory(new PropertyValueFactory<InstrumentFunc,String>("name"));
        instrumentFuncTableNote.setCellValueFactory(new PropertyValueFactory<InstrumentFunc,String>("note"));

        InstrumentFuncUpdateList();
    }
    private void InstrumentFuncUpdateList(){
        instrumentFuncList = new InstrumentFunc().getData();
        instrumentFuncTable.setItems(instrumentFuncList);
    }
    @FXML void instrumentFuncDelete_Tap(){
        InstrumentFunc If = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getFunction();
        if (If != null){
            If.delete();
            InstrumentFuncUpdateList();
            instrumentsTreeUpdate();
        }
    }
    @FXML void instrumentFuncUpdate_Tap(){
        InstrumentFunc If = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getFunction();
        if (If != null){
            EditDialog<InstrumentFunc> e = new EditDialog<InstrumentFunc>(If);
            if(e.getRet()) {
                If.update();
                InstrumentFuncUpdateList();
                instrumentsTreeUpdate();
            }
        }
    }
    @FXML void instrumentFuncInsert_Tap(){
        InstrumentFunc If = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue().getFunction();
        if (If == null) If = new InstrumentFunc(0,"Function", "Note");
        EditDialog<InstrumentFunc> e = new EditDialog<InstrumentFunc>(If);
        if(e.getRet()){
            If.insert();
            InstrumentFuncUpdateList();
            instrumentsTreeUpdate();
        }
    }

    //--------------Automation Instruments----------------------------------
    public ObservableList<Instrument> instrumentList;
    @FXML TableView<Instrument> instrumentTable;
    @FXML TableColumn<Instrument, Manufacturer> instrumenTableManufacturer;
    @FXML TableColumn<Instrument, String> instrumenTableType;
    @FXML TableColumn<Instrument, InstrumentFunc> instrumenTableFunc;
    @FXML TableColumn<Instrument, String> instrumenTableOrderNum;
    @FXML TableColumn<Instrument, String> instrumenTableParams;
    @FXML TableColumn<Instrument, Integer> instrumenTableSpare;
    @FXML Button But_Instrument_Insert;
    @FXML Button But_Instrument_Update;
    @FXML Button But_Instrument_Del;

    private void InstrumentInit(){
        instrumenTableManufacturer.setCellValueFactory(new PropertyValueFactory<Instrument,Manufacturer>("manufacturer"));
        instrumenTableType.setCellValueFactory(new PropertyValueFactory<Instrument,String>("type"));
        instrumenTableFunc.setCellValueFactory(new PropertyValueFactory<Instrument,InstrumentFunc>("function"));
        instrumenTableOrderNum.setCellValueFactory(new PropertyValueFactory<Instrument,String>("orderNumber"));
        instrumenTableParams.setCellValueFactory(new PropertyValueFactory<Instrument,String>("parameters"));
        instrumenTableSpare.setCellValueFactory(new PropertyValueFactory<Instrument,Integer>("spare"));
        But_Instrument_Update.setDisable(true);
        instrumentTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> instrumentShowDetails(newValue));

        InstrumentUpdateList();
        instrumentsTreeUpdate();
    }
    private void InstrumentUpdateList(){
        instrumentList = new Instrument().getData();
        instrumentTable.setItems(instrumentList);
        EquipmentUpdateList();
    }
    private void instrumentShowDetails(Instrument instrument){
        if(instrument != null) {
            But_Instrument_Update.setDisable(false);
        }
    }
    @FXML void But_Instrument_Insert_Tap(){
        Instrument it = null;
        Instrument i = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue();
        if(i!= null) it= new Instrument("","",i.getManufacturer(),i.getFunction());
        else it = new Instrument();
        EditDialog<Instrument> E = new EditDialog<Instrument>(it);
        if(E.getRet()) {
            it.insert();
            InstrumentUpdateList();
            instrumentsTreeUpdate();
        }
    }
    @FXML void But_Instrument_Update_Tap(){
        //Instrument i = instrumentTable.getSelectionModel().getSelectedItem();
        Instrument i = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue();
        if(i== null) return;
        EditDialog<Instrument> E = new EditDialog<Instrument>(i);
        if(E.getRet()) {
            i.update();
            InstrumentUpdateList();
            instrumentsTreeUpdate();
        }
    }
    @FXML void But_Instrument_Del_Tap(){
        Instrument i = InstrumentsTreeTableView.getSelectionModel().getSelectedItem().getValue();// instrumentTable.getSelectionModel().getSelectedItem();
        if(i== null) return;
        i.delete();
        InstrumentUpdateList();
        instrumentsTreeUpdate();
    }

    @FXML TreeTableView<Instrument> InstrumentsTreeTableView;
    //@FXML TreeTableColumn<Equipment,String> zoneTreeTableColumnZone;

    TreeItem<Instrument> instrumentTreeItem ;


    private void instrumentsTree(){


        TreeTableColumn<Instrument, String> funcColumn =
                new TreeTableColumn<>("Функция");
        funcColumn.setPrefWidth(150);
        funcColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getFunction().getName())
        );

        TreeTableColumn<Instrument, String> typeColumn =
                new TreeTableColumn<>("Тип");
        typeColumn.setPrefWidth(150);
        typeColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getName())
        );

        TreeTableColumn<Instrument, String> orderNumColumn =
                new TreeTableColumn<>("Заказной номер");
        orderNumColumn.setPrefWidth(150);
        orderNumColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getOrderNumber())
        );

        TreeTableColumn<Instrument, String> parametersColumn =
                new TreeTableColumn<>("Параметры");
        parametersColumn.setPrefWidth(200);

        parametersColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getParameters())
        );

        /*TreeTableColumn<Instrument, String> pageColumn =
                new TreeTableColumn<>("Кол.Зип");
        pageColumn.setPrefWidth(40);
        pageColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSpare().toString())
        );*/

        TreeTableColumn<Instrument, String> noteColumn =
                new TreeTableColumn<>("Примечание");
        noteColumn.setPrefWidth(200);
        noteColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Instrument, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getNote())
        );


        //zoneTreeTableView = new TreeTableView<>(zoneTreeItem, depIcon);
        //zoneTreeTableView.getColumns().add(zoneTreeTableColumnZone);
        TreeTableColumn<Instrument, String> instrumentsTreeTableColumn = new TreeTableColumn<>("Производитель");

        instrumentsTreeTableColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Instrument, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getName()));
        instrumentsTreeTableColumn.setPrefWidth(300);
        InstrumentsTreeTableView.setRoot(instrumentTreeItem);
        InstrumentsTreeTableView.setTreeColumn(instrumentsTreeTableColumn);
        InstrumentsTreeTableView.getColumns().setAll(instrumentsTreeTableColumn,typeColumn, orderNumColumn, parametersColumn, noteColumn);
        instrumentsTreeUpdate();

    }
    public void instrumentsTreeUpdate(){
        instrumentTreeItem = new TreeItem<Instrument>(new Instrument("Приборы", "",null,null)); //Here must be certainly object Zone
        for(Manufacturer M: manufacturerList){
            TreeItem<Instrument> mt = new TreeItem<>(new Instrument(M.getName(), M.getNote(),M,null));
            mt.setExpanded(true);
            for(InstrumentFunc F : instrumentFuncList){
                TreeItem<Instrument> Ft = new TreeItem<>(new Instrument(F.getName(), F.getNote(),M,F));
                boolean added = false;
                for(Instrument i : instrumentList){
                    if(i.getManufacturer().getId() == M.getId() && i.getFunction().getId() == F.getId()) {

                            if (!added) {
                                Ft.setExpanded(true);
                                mt.getChildren().add(Ft);
                                added = true;
                            }
                            TreeItem<Instrument> inst = new TreeItem<>(i);
                            Ft.getChildren().add(inst);


                    }
                }
            }
            instrumentTreeItem.getChildren().add(mt);

        }
        InstrumentsTreeTableView.setRoot(instrumentTreeItem);
    }

    //---------Equipment------------------------------
    public ObservableList<Equipment> equipmentList;
    @FXML TableView<Equipment> equipmentTable;
    @FXML TableColumn<Equipment, String> equipmentTablePos;
    @FXML TableColumn<Equipment, String> equipmentTableName;
    @FXML TableColumn<Equipment, SubZone> equipmentTableSubzone;
    @FXML TableColumn<Equipment, Instrument> equipmentTableInstrument;
    @FXML TableColumn<Equipment, String>  equipmentTableApointment;
    @FXML TableColumn<Equipment, String>  equipmentTablePage;
    @FXML TableColumn<Equipment, String> equipmentTableNote;

    @FXML Button But_EquipmentUpdate;
    @FXML Button But_EquipmentDelete;
    @FXML Button But_EquipmentInsert;

    private void EquipmentInit(){
        equipmentTablePos.setCellValueFactory(new PropertyValueFactory<Equipment,String>("position"));
        equipmentTableName.setCellValueFactory(new PropertyValueFactory<Equipment,String>("name"));
        equipmentTableSubzone.setCellValueFactory(new PropertyValueFactory<Equipment,SubZone>("subZone"));
        equipmentTableInstrument.setCellValueFactory(new PropertyValueFactory<Equipment,Instrument>("instrment"));
        equipmentTableApointment.setCellValueFactory(new PropertyValueFactory<Equipment,String>("appointment"));
        equipmentTablePage.setCellValueFactory(new PropertyValueFactory<Equipment,String>("schematicDiagramPage"));
        equipmentTableNote.setCellValueFactory(new PropertyValueFactory<Equipment,String>("note"));

        But_EquipmentUpdate.setDisable(true);
        equipmentTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> EquipmentShowDetails(newValue));

        EquipmentUpdateList();
    }
    private void EquipmentUpdateList(){
        equipmentList = new Equipment().getData();
        equipmentTable.setItems(equipmentList);
        zoneTreeUpdate();
    }
    private void EquipmentShowDetails(Equipment equipment){
        if(equipment != null) {
            But_EquipmentUpdate.setDisable(false);
        }
    }
    @FXML void But_EquipmentInsert_Tap(){
        Equipment i= new Equipment();
        Equipment it = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue();
        i = (Equipment)it.clone();
        i.setSubZone(it.getSubZone());
        EditDialog<Equipment> E = new EditDialog<Equipment>(i);
        if(E.getRet()) {
            i.insert();
            EquipmentUpdateList();
            zoneTreeUpdate();
        }
    }
    @FXML void But_EquipmentDelete_Tap(){
        Equipment i = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue(); //equipmentTable.getSelectionModel().getSelectedItem();
        i.delete();
        EquipmentUpdateList();
        zoneTreeUpdate();
    }
    @FXML void But_EquipmentUpdate_Tap(){
        Equipment i = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue();
        EditDialog<Equipment> E = new EditDialog<Equipment>(i);
        if(E.getRet()) {
            i.update();
            EquipmentUpdateList();
            zoneTreeUpdate();
        }
    }

    //--------Trouble---------------------------------
    @FXML void But_TroubleUpdate_Tap(){
        Manufacturer manufacturer = new Manufacturer(1, "Rectifier", "Semiconductor equipment");
        EditDialog<Manufacturer> M = new EditDialog<Manufacturer>(manufacturer);
        // man;
        //man = M.getModel();
        manufacturer.update();
    }
    @FXML void But_TroubleInsert_Tap(){
        Manufacturer manufacturer = new Manufacturer(1, "Rectifier", "Semiconductor equipment");
        EditDialog<Manufacturer> M = new EditDialog<Manufacturer>(manufacturer);
        manufacturer.insert();
    }
    @FXML void But_TroubleDelete_Tap(){
        Manufacturer manufacturer = new Manufacturer(1, "Rectifier", "Semiconductor equipment");
        manufacturer.delete();
    }
    @FXML void But_newForce_Tap(){

        //PLC plc = new PLC();
        //EditDialog<PLC> dialog = new EditDialog<PLC>(plc,"PLC edit");
//        Zone zone = new Zone(0,"PU020","Подача древесины","Из-под силосов до мойки щепы");
//        EditDialog dialog = new EditDialog("Редактирование зоны");
//        String s1 = new String(zone.getPosition());
//        dialog.addRow("Позиция",s1);
//        dialog.addRow("Название",zone.getName());
//        dialog.addRow("Описание",zone.getNote());
//
//
//        //dialog.addRow("title","some value");
//        //dialog.addRow("title",zoneList);
//        dialog.showDialog();
//        System.out.println(s1);

        //передаем переменные в диалоговое окно по ссылке

//        SubZone subZone = new SubZone(0,"prepress", "2311", "for air deleting",1);
//        EditDialog<SubZone> dialog2 = new EditDialog<SubZone>("Редактирование зоны", subZone);
//        System.out.println(subZone.getName() + subZone.getNote() + subZone.getZone());
//
//        Zone zone2 = new Zone(0,"PU020","Подача древесины","Из-под силосов до мойки щепы");
//        EditDialog<Zone> dialog3 = new EditDialog<Zone>("Редактирование зоны", zone2);
//        System.out.println(zone2.getPosition() + zone2.getName() + zone2.getNote());
//
//        PLC plc = new PLC(0,"plc050", "none");
//        EditDialog<PLC> dialog4 = new EditDialog<PLC>("Редактирование PLC", plc);
//        System.out.println(plc.getName() + plc.getPassword());
//
//        Engineer e = new Engineer(0,"Ruslan", "Galitckiy", "C");
//        EditDialog<Engineer> dialog5 = new EditDialog<Engineer>("Редактирование Инженера", e);
//        System.out.println(e.getLastName() + e.getFirstName() + e.getShift());
    }


    //================Subzone========================================
    private void subzoneInit(){
        SubZone.setConnection(connection.getConnection());
//        subzoneTableName.setCellValueFactory(new PropertyValueFactory<SubZone,String>("name"));
//        subzoneTableZone.setCellValueFactory(new PropertyValueFactory<SubZone,String>("zoneName"));
//        subzoneTableNote.setCellValueFactory(new PropertyValueFactory<SubZone,String>("note"));

        subzoneUpdateList();

    }
    private void subzoneUpdateList(){
        subZoneList = new SubZone().getData();
        //subzoneTableview.setItems(subZoneList);
        //zoneTreeUpdate();
    }
    @FXML private void MI_subzoneDel_Tap(){
        SubZone subZone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone();
        if(subZone != null){
            //Main.showSubZoneEditDialog(subZone, zoneList);
            subZone.delete();
            subzoneUpdateList();
            zoneTreeUpdate();
        }
    }
    @FXML private void MI_subzoneAdd_Tap(){
//        SubZone subZone = new SubZone();
//        System.out.println("----controller----");
//        for(Zone zone :zoneList){
//            System.out.println(zone.getName());
//        }
        //SubZone subZone = subzoneTableview.getSelectionModel().getSelectedItem();
        SubZone subZone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone();
        if(subZone == null) subZone = new SubZone();
        //Main.showSubZoneEditDialog(subZone, zoneList);
        EditDialog E = new EditDialog(subZone);
        if(E.getRet()) subZone.insert();
        subzoneUpdateList();
        zoneTreeUpdate();
    }
    @FXML private void MI_subzoneUpdate_Tap(){
        //SubZone subZone = subzoneTableview.getSelectionModel().getSelectedItem();
        SubZone subZone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone();
        if(subZone == null) return;
        //Main.showSubZoneEditDialog(subZone, zoneList);
        EditDialog E = new EditDialog(subZone);
        if(E.getRet()) subZone.update();
        subzoneUpdateList();
        zoneTreeUpdate();
    }

    //================INICIATOR====================================
    private void iniciatorInit(){
        //Iniciator inicialization
        Iniciator.setConnection(connection.getConnection());
        iniciatorTableName.setCellValueFactory(new PropertyValueFactory<Iniciator,String>("Name"));
        iniciatorTableNote.setCellValueFactory(new PropertyValueFactory<Iniciator,String>("Note"));

        iniciatorUpdateList();
        iniciatorTableView.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> iniciatorShowDetails(newValue));
        But_iniciatorDel.setDisable(true);
        But_iniciatorUpdate.setDisable(true);
    }
    private void iniciatorShowDetails(Iniciator iniciator){
        if(iniciator != null) {
            //TW_iniciatorName.setText(iniciator.getName());
            //TW_iniciatorNote.setText(iniciator.getNote());
            But_iniciatorUpdate.setDisable(false);
            But_iniciatorDel.setDisable(false);
        }
    }
    private void iniciatorUpdateList(){
        Iniciator iniciator = new Iniciator();
        iniciatorList = iniciator.getData();
        iniciatorTableView.setItems(iniciatorList);
        CombIniciator.setItems(iniciatorList);
        CombIniciator_Filter.setItems(iniciatorList);
    }
    @FXML void But_InicDel_Tap(){
        Iniciator initiator = iniciatorTableView.getSelectionModel().selectedItemProperty().getValue();
        if(DeleteDialog(initiator)){
            iniciatorUpdateList();
        }
    }

    @FXML void But_InicAdd_Tap(){
        Iniciator iniciator = new Iniciator();
        EditDialog E = new EditDialog(iniciator);
        iniciator.insert();
        iniciatorUpdateList();
    }
    @FXML void But_InicUpdate_Tap(){
        Iniciator initiator = iniciatorTableView.getSelectionModel().selectedItemProperty().getValue();
        if (initiator == null) {
            AlertInfo("Выберите " + "Строку" + "для редактирования");
            return;
        }
        EditDialog E = new EditDialog(initiator);
        if(E.getRet()) {
            initiator.update();
            iniciatorUpdateList();
        }
    }

    //===============PLC Metods==================================
    private void plcInit(){
        //PLC initialization
        PLC.setConnection(connection.getConnection());
        plcTableName.setCellValueFactory(new PropertyValueFactory<PLC,String>("name"));
        plcTablePass.setCellValueFactory(new PropertyValueFactory<PLC,String>("password"));
        plcUpdateList();

        plcTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> plcShowDetails(newValue));

        But_PLCDel.setDisable(true);
        But_PLCUpdate.setDisable(true);

    }
    private void plcShowDetails(PLC plc){
        if(plc != null){
            But_PLCDel.setDisable(false);
            But_PLCUpdate.setDisable(false);
        }

    }
    private void plcUpdateList(){
        PLC plc = new PLC();
        plcList = plc.getData();
        CombPLC.setItems(plcList);
        plcTable.setItems(plcList);
        CombPLC_Filter.setItems(plcList);
    }
    @FXML void But_PLCDel_Tap(){
        PLC plc = plcTable.getSelectionModel().selectedItemProperty().getValue();
        if(DeleteDialog(plc)){
            plcUpdateList();
        }

    }
    @FXML void But_PLCAdd_Tap(){
//        int id = 0;
//        String plcLabel = TW_PLCLabel.getText();
//        String plcPass = TW_PLCPass.getText();
        PLC plc = new PLC();
        EditDialog E = new EditDialog(plc);
        if(E.getRet()) {
            plc.insert();
            plcUpdateList();
        }
    }
    @FXML void But_PLCUpdate_Tap(){
        PLC plc = plcTable.getSelectionModel().selectedItemProperty().getValue();
        if (plc == null) {
            AlertInfo("Выберите " + "Строку" + "для редактирования");
            return;
        }
        EditDialog E = new EditDialog(plc);
        if (E.getRet()) {
            plc.update();
            plcUpdateList();
        }
    }
    @FXML void MI_AddPLC_Tap(){
        PLC plc = new PLC(0,"","");
        Main.showPLCEditDialog(plc);
        if(plc.getName() != "") {
            plc.insert();
            plcUpdateList();
            for (PLC p : plcList){
                if(p.getName() == plc.getName()){
                    plc = p;
                }
            }
            CombPLC.setValue(plc);
        }
    }

    //===============Zone Metods=================================
    private void zoneInit(){
        //Zone initialization
        Zone.setConnection(connection.getConnection());
//        ZoneNameColumn.setCellValueFactory(new PropertyValueFactory<Zone,String>("name"));
//        ZonePosColumn.setCellValueFactory(new PropertyValueFactory<Zone,String>("position"));
//        ZoneNoteColumn.setCellValueFactory(new PropertyValueFactory<Zone,String>("Note"));

        zoneUpdateList();

//        ZoneTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> zoneShowDetails(newValue));
//        But_ZoneDel.setDisable(true);
   //     But_ZoneUpdate.setDisable(true);
    }
    private void zoneShowDetails(Zone zone){
        if(zone != null){
//            TW_Zone_name.setText(zone.getName());
//            TW_Zone_pos.setText(zone.getPosition());
//            TW_Zone_note.setText(zone.getNote());

         //   But_ZoneDel.setDisable(false);
         //   But_ZoneUpdate.setDisable(false);
        }
    }
    private void zoneUpdateList(){
        Zone z = new Zone();
        zoneList = z.getData();
//        ZoneTable.setItems(zoneList);
        //zoneTreeUpdate();
        //zoneList.stream().forEach((zone) -> {zoneTreeItem.getChildren().add(new TreeItem<>(zone));});
    }
    @FXML void But_ZoneDel_Tap(){
        //Zone zone = ZoneTable.getSelectionModel().selectedItemProperty().getValue();
        Zone zone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone().getZone();
        if(zone != null) {
            zone.delete();
            zoneUpdateList();
            zoneTreeUpdate();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            //alert.setHeaderText("Look, an Information Dialog");
            alert.setContentText("Выберите зону!");
            alert.showAndWait();
        }

    }
    @FXML void But_ZoneAdd_Tap(){
        //Zone zone = new Zone(0, TW_Zone_pos.getText(), TW_Zone_name.getText(), TW_Zone_note.getText());
        Zone zone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone().getZone();
        if(zone == null) zone = new Zone();
        EditDialog<Zone> E = new EditDialog<Zone>(zone);
        zone.insert();
        zoneUpdateList();
        zoneTreeUpdate();
    }
    @FXML void But_ZoneUpdate_Tap(){
        //Zone zone = ZoneTable.getSelectionModel().selectedItemProperty().getValue();
        Zone zone = zoneTreeTableView.getSelectionModel().getSelectedItem().getValue().getSubZone().getZone();
        if (zone == null) { zone = new Zone();}
            EditDialog<Zone> E = new EditDialog<Zone>(zone);
        zone.update();

//            zone.setName(TW_Zone_name.getText());
//            zone.setPosition(TW_Zone_pos.getText());
//            zone.setNote( TW_Zone_note.getText());
//            zone.update();
//            System.out.println("zone updated");
        //}
//        else{
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information Dialog");
//            //alert.setHeaderText("Look, an Information Dialog");
//            alert.setContentText("Выберите зону!");
//            alert.showAndWait();
//        }
        zoneUpdateList();
        zoneTreeUpdate();
    }


//    @FXML TreeTableView<Zone> zoneTreeTableView;
//    @FXML TreeTableColumn<Zone,String> zoneTreeTableColumnZone;
//    @FXML TreeTableColumn<SubZone, String> zoneTreeTableColumnSubZone;

    //TreeItem<Zone> zoneTreeItem ;

//    private void zoneTree(){
//        zoneTreeItem = new TreeItem<>(new Zone(0,"All","Factory","")); //Here must be certainly object Zone
//        for(Zone z: zoneList){
//            TreeItem<Zone> t = new TreeItem<>(z);
////            for(SubZone sz: subZoneList){
////                if(sz.getZone().getId() == z.getId()){
////                    TreeItem<SubZone> szt = new TreeItem<>(sz);
////                    t.getChildren().add(szt);
////                }
////            }
//            zoneTreeItem.getChildren().add(t);
//            t.setExpanded(true);
//        }
//        //zoneTreeTableView = new TreeTableView<>(zoneTreeItem, depIcon);
//        //zoneTreeTableView.getColumns().add(zoneTreeTableColumnZone);
//
//        zoneTreeTableColumnZone.setCellValueFactory((TreeTableColumn.CellDataFeatures<Zone, String> p) ->
//                new ReadOnlyStringWrapper(p.getValue().getValue().getName()));
//            //Subzone column
//        zoneTreeTableColumnSubZone.setCellValueFactory((TreeTableColumn.CellDataFeatures<SubZone, String> p) ->
//                new ReadOnlyStringWrapper(p.getValue().getValue().getName()));
//
//        zoneTreeTableView.setRoot(zoneTreeItem);
//        zoneTreeTableView.setTreeColumn(zoneTreeTableColumnZone);
//    }

    @FXML TreeTableView<Equipment> zoneTreeTableView;
    //@FXML TreeTableColumn<Equipment,String> zoneTreeTableColumnZone;

    TreeItem<Equipment> zoneTreeItem ;


    private void zoneTree(){


        TreeTableColumn<Equipment, String> nameColumn =
                new TreeTableColumn<>("Название");
        nameColumn.setPrefWidth(150);
        nameColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Equipment, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getPosition() + param.getValue().getValue().getName())
        );

        TreeTableColumn<Equipment, String> instrumentColumn =
                new TreeTableColumn<>("Прибор");
        instrumentColumn.setPrefWidth(150);
        instrumentColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Equipment, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getInstrment().toString())
        );

        TreeTableColumn<Equipment, String> apointmentColumn =
                new TreeTableColumn<>("Назначение");
        apointmentColumn.setPrefWidth(200);

        apointmentColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Equipment, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getAppointment())
        );

        TreeTableColumn<Equipment, String> pageColumn =
                new TreeTableColumn<>("Страница");
        pageColumn.setPrefWidth(40);
        pageColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Equipment, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSchematicDiagramPage())
        );

        TreeTableColumn<Equipment, String> noteColumn =
                new TreeTableColumn<>("Примечание");
        noteColumn.setPrefWidth(200);
        noteColumn.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Equipment, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getNote())
        );


        //zoneTreeTableView = new TreeTableView<>(zoneTreeItem, depIcon);
        //zoneTreeTableView.getColumns().add(zoneTreeTableColumnZone);
        TreeTableColumn<Equipment, String> zoneTreeTableColumnZone = new TreeTableColumn<>("Оборудование");

        zoneTreeTableColumnZone.setCellValueFactory((TreeTableColumn.CellDataFeatures<Equipment, String> p) ->
                new ReadOnlyStringWrapper(p.getValue().getValue().getPosition() + " " + p.getValue().getValue().getName()));
        zoneTreeTableColumnZone.setPrefWidth(300);
        zoneTreeTableView.setRoot(zoneTreeItem);
        zoneTreeTableView.setTreeColumn(zoneTreeTableColumnZone);
        zoneTreeTableView.getColumns().setAll(zoneTreeTableColumnZone,apointmentColumn, instrumentColumn, pageColumn, noteColumn);
        zoneTreeUpdate();
    }
    public void zoneTreeUpdate(){
        zoneTreeItem = new TreeItem<>(new Equipment("Factory"," ",new SubZone())); //Here must be certainly object Zone

        for(Zone z: zoneList){
            TreeItem<Equipment> t = new TreeItem<>(new Equipment(z.getName(), z.getPosition(), new SubZone(z),z.getNote()));
            for(SubZone sz: subZoneList){
                if(sz.getZone().getId() == z.getId()){
                    TreeItem<Equipment> szt = new TreeItem<>(new Equipment(sz.getName(), sz.getPosition(), sz, sz.getNote()));
                    for(Equipment eq :equipmentList){
                        if(eq.getSubZone().getId() == sz.getId()){
                            TreeItem<Equipment> eqt = new TreeItem<>(eq);
                            szt.getChildren().add(eqt);
                        }
                    }
                    t.getChildren().add(szt);
                }
                t.setExpanded(true);
            }
            zoneTreeItem.getChildren().add(t);
            t.setExpanded(true);
        }
        zoneTreeTableView.setRoot(zoneTreeItem);
    }
//    private TreeItem<String> createTreeItem(TreeMap<String, List<String>> data,
//                                            String rootKey) {
//        TreeItem<String> item = new TreeItem<>();
//        item.setValue(rootKey);
//        item.setExpanded(true);
//
//        List<String> childData = data.get(rootKey);
//        if (childData != null) {
//            childData.stream().map(child -> createTreeItem(data, child))
//                    .collect(Collectors.toCollection(item::getChildren));
//        }
//
//        String valueName = item.getValue();
//        //String sorteV = dc.getSortedfuncAll().get(valueName);
//        item.setValue((dc.getSortedfuncAll().get(valueName)));
//        return item;
//    }


    //==================Engineer Metods=======================
    private void engneerInit(){
        Engineer.setConnection(connection.getConnection());
        EngFnameColumn.setCellValueFactory(new PropertyValueFactory<Engineer,Integer>("firstName"));
        EngLnameColumn.setCellValueFactory(new PropertyValueFactory<Engineer, String>("lastName"));
        EngShiftColumn.setCellValueFactory(new PropertyValueFactory<Engineer, String>("shift"));

        engineerUpdateList();
        EngTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> engineerShowDetail(newValue));
        But_EngDel.setDisable(true);
        But_EngUpdate.setDisable(true);
    }
    private void engineerShowDetail(Engineer eng){
        if(eng != null){
            But_EngDel.setDisable(false);
            But_EngUpdate.setDisable(false);
        }
    }
    private void engineerUpdateList(){
        Engineer e = new Engineer();
        engList = e.getData();
        EngTable.setItems(engList);
        CombEngineer.setItems(engList);
        CombEngineer_Filter.setItems(engList);
    }
    @FXML void But_EngDel_Tap(){
        Engineer eng = EngTable.getSelectionModel().selectedItemProperty().getValue();
        if(DeleteDialog(eng)){
            engineerUpdateList();
        }
    }
    @FXML void But_EngAdd_Tap(){
        Engineer engineer = new Engineer();
        EditDialog E= new EditDialog(engineer);
        if(E.getRet()){
            engineer.insert();
            engineerUpdateList();
        }

    }
    @FXML void But_EngUpdate_Tap(){
        Engineer eng = EngTable.getSelectionModel().selectedItemProperty().getValue();
        if(eng == null){
            AlertInfo("Выберите строку");
            return;
        }
        EditDialog E = new EditDialog(eng);
        if(E.getRet()){
            eng.update();
            engineerUpdateList();
        }
    }

    //==================Force Metods==========================
    @FXML Label Lab_forceEng;
    @FXML Label Lab_forceDate;
    @FXML Label Lab_forcePLC;
    @FXML Label Lab_forceAdr;
    @FXML Label Lab_forceInic;
    @FXML Label Lab_forceNote;
    @FXML Label Lab_forceStatus;


    private void forceInit(){
        Force.setConnection(connection.getConnection());
        ForcEngName.setCellValueFactory(new PropertyValueFactory<Force,Engineer>("engineer"));
        //ForcIniciatorName.setCellValueFactory(new PropertyValueFactory<Force,String>("position"));

        ForcDate.setCellValueFactory(new PropertyValueFactory<Force,Date>("date"));
        ForcPLC.setCellValueFactory(new PropertyValueFactory<Force,PLC>("plc"));
        ForcAdress.setCellValueFactory(new PropertyValueFactory<Force,String>("adress"));
        ForcNote.setCellValueFactory(new PropertyValueFactory<Force,String>("note"));
        ForcIniciatorName.setCellValueFactory(new PropertyValueFactory<Force,Iniciator>("iniciator"));


        forceUpdateList();
//        forceList =Force.getForces();
//        ForseTable.setItems(forceList);
        ForseTable.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> forceShowDetails(newValue));

        But_ForceDel.setDisable(true);
        But_ForceUpdate.setDisable(true);
    }
    private void forceShowDetails(Force force){
        Locale.setDefault(Locale.US);
        if(force != null) {
            CombEngineer.setValue(force.getEngineer());
            CombPLC.setValue(force.getPlc());
            CombIniciator.setValue(force.getIniciator());
            TW_ForseAddr.setText(force.getAdress());
            TW_ForceNote.setText(force.getNote());

            Instant instant = force.getDate().toInstant();
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            But_ForceUpdate.setDisable(false);
            But_ForceDel.setDisable(false);
            DatPicker_ForceDate.setValue(localDate);
        }
    }
    private void forceUpdateList(){
        Force f = new Force();
        forceList = f.getData();
        ForseTable.setItems(forceList);
    }
    @FXML void But_ForceDel_Tap(){
        Force force = ForseTable.getSelectionModel().selectedItemProperty().getValue();
        if(DeleteDialog(force)){
            forceUpdateList();
        }
//
//        if(force != null){
//            force.delete();
//        }
//        else {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Предупреждение");
//            alert.setHeaderText(null);
//            alert.setContentText("Выберите строку перед удалением");
//            alert.showAndWait();
//        }
    }
    @FXML void But_ForceAdd_Tap(){
        LocalDate localDate = DatPicker_ForceDate.getValue();
        //System.out.println(localDate);
        java.util.Date javaDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth() /*localDate.toEpochDay()*/);

        //java.util.Date javaDate = new Date(localDate.toEpochDay());
        Force force = new Force(0,
                CombEngineer.getSelectionModel().getSelectedItem().getId(),
                CombIniciator.getSelectionModel().getSelectedItem().getId(),
                javaDate,
                CombPLC.getSelectionModel().getSelectedItem().getId(),
                TW_ForseAddr.getText(),
                TW_ForceNote.getText(),
                engList,plcList,iniciatorList);

        EditDialog<Force> d = new EditDialog<Force>(force);
        force.insert();
        forceUpdateList();
    }
    @FXML void MI_ForceAdd_Tap(){
        Trouble t = troubleTable.getSelectionModel().getSelectedItem();

        if(t != null) {
            Force f = new Force();
            f.setTrouble(t);
            EditDialog E = new EditDialog(f);
            if (E.getRet()) {
                f.insert();
                forceUpdateList();
                troubleUpdateList();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Выберите строку");
            alert.showAndWait();
        }
    }
    @FXML void MI_TroubleForceEdit_Tap(){
        Force f = TroubleForseTable.getSelectionModel().getSelectedItem();
        if(f != null){
            EditDialog E = new EditDialog(f);
            if(E.getRet()){
                f.update();
                Trouble t = troubleTable.getSelectionModel().getSelectedItem();
                if(t!=null) {
                    troubleForcesList = Force.getDataFiltered(t);
                    TroubleForseTable.setItems(troubleForcesList);
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setHeaderText(null);
            alert.setContentText("Выберите строку Force");
            alert.showAndWait();
        }
    }
    @FXML void But_ForceUpdate_Tap(){
        LocalDate localDate = DatPicker_ForceDate.getValue();

        java.util.Date javaDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth() /*localDate.toEpochDay()*/);
        Force force = ForseTable.getSelectionModel().selectedItemProperty().getValue();

        if (force != null) {
            force.setEngineer(CombEngineer.getSelectionModel().getSelectedItem());
            force.setIniciator(CombIniciator.getSelectionModel().getSelectedItem());
            force.setDate(javaDate);
            force.setPlc(CombPLC.getSelectionModel().getSelectedItem());
            force.setAdress(TW_ForseAddr.getText());
            force.setNote(TW_ForceNote.getText());

            EditDialog<Force> d = new EditDialog<Force>(force);
            force.update();
        }

        forceUpdateList();
    }
    @FXML void But_ForceFilter_Tap(){
        int eng_id = 0, inic_id, PLC_id;
        Engineer eng = CombEngineer_Filter.getValue();
        Iniciator inic = CombIniciator_Filter.getValue();
        PLC plc = CombPLC_Filter.getValue();

        if(eng != null)  eng_id = eng.getId();
        else eng_id = 0;

        if(inic != null) inic_id = inic.getId();
        else inic_id = 0;

        if(plc != null) PLC_id = plc.getId();
        else PLC_id = 0;

        forceList = Force.getDataFiltered(eng_id, inic_id, PLC_id, null, null);
        ForseTable.setItems(forceList);
    }
    @FXML void But_ForceFilterReset_Tap(){
        CombPLC_Filter.setValue(null);
        CombEngineer_Filter.setValue(null);
        CombIniciator_Filter.setValue(null);
        Force f = new Force();
        forceList = f.getData();
        ForseTable.setItems(forceList);
    }



    //Images
    private  void imageInit(){
        ImageBlob.setConnection(connection.getConnection());
        ImageUpdateList();
        Comb_images.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) -> ImageSelChanged(newValue));
    }
    private void ImageSelChanged(ImageBlob imageBlob){
        ImageView1.setImage(imageBlob.getImage());
    }
    //Image metods
    Image img = null;
    List<File> list;
    int numerator = 0;

    @FXML void But_ChooseImageFile_Tap(){
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        list = fileChooser.showOpenMultipleDialog(null);
        if (list != null) {
            File file = list.get(0);
            String path =  file.toURI().toString();
            img = new Image(path);
            System.out.println(list.size());
            numerator = 1;
            TW_imageName.setText(file.getName());
            ImageView1.setImage(img);
            for (File _file : list) {
                //openFile(file);
                System.out.println(_file.getPath());
            }
        }

        /*File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getName());
        }
        else {
            System.out.println("File selection cancelled.");
        }*/

    }
    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("Выбор файла изображения");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

    }
    @FXML void But_imageNext_Tap(){
        if(list.size() > numerator + 1){
            numerator++;
            File file = list.get(numerator);
            String path =  file.toURI().toString();
            img = new Image(path);
            System.out.println(numerator + path);
            TW_imageName.setText(file.getName());
            ImageView1.setImage(img);
            ImageUpdateList();
        }
    }
    @FXML void But_imagePrev_Tap(){
        if(numerator > 0){
            numerator--;
            File file = list.get(numerator);
            String path =  file.toURI().toString();
            System.out.println(numerator + path);
            img = new Image(path);
            TW_imageName.setText(file.getName());
            ImageView1.setImage(img);
            ImageUpdateList();
        }
    }
    @FXML void But_imageAdd_Tap(){
        if(list.get(numerator-1) !=  null){
            File file = list.get(numerator-1);
    //        String path =  file.toURI().toString();
    //        img = new Image(path);
            ImageBlob image = new ImageBlob(file.getName(), 1);
            image.Add(file);
            ImageUpdateList();
        }
    }
    @FXML void But_ImageDel_Tap(){
        Comb_images.getSelectionModel().getSelectedItem().Del();
        ImageUpdateList();
    }
    private void ImageUpdateList(){
        imageBlob = ImageBlob.Get();
        Comb_images.setItems(imageBlob);
    }
    @FXML void But_getClipboardImage_Tap(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        if(clipboard.hasContent(DataFormat.IMAGE)){
            ImageView1.setImage(clipboard.getImage());
            ImageBlob im = new ImageBlob("clipboardimage",clipboard.getImage(),0);
            im.Add();
            ImageUpdateList();
        }
    }
    private void openFile(File file) {
        /*try{
            desktop.open(file);

        }
        catch(IOException e){
            e.printStackTrace();
        }*/
    }

    // Troubles metods

    @FXML Label Lab_troubleZone;
    @FXML Label Lab_troubleEquipment;
    @FXML Label Lab_troubleNote;
    @FXML Label Lab_troubleDate;
    @FXML Label Lab_troubleReason;
    @FXML Label Lab_troubleActions;
    @FXML Label Lab_troubleEngineer;

    @FXML TableView<Trouble> troubleTable;
    @FXML TableColumn<Trouble,Equipment> troubleTable_Equipment;
    @FXML TableColumn<Trouble,Date> troubleTable_Data;
    @FXML TableColumn<Trouble,String> troubleTable_Note;
    @FXML TableColumn<Trouble,String> troubleTable_Reason;
    @FXML TableColumn<Trouble,String> troubleTable_Actions;
    @FXML TableColumn<Trouble,Boolean> troubleTable_PPR;
    @FXML TableColumn<Trouble,Engineer> troubleTable_Eng;

    private ObservableList troubleForcesList;
    @FXML TableView<Force> TroubleForseTable;
    @FXML TableColumn<Force,Engineer>  TroubleForcEngName;
    @FXML TableColumn<Force,Iniciator>  TroubleForcIniciatorName;
    @FXML TableColumn<Force,Date>    TroubleForcDate;
    @FXML TableColumn<Force,PLC>  TroubleForcPLC;
    @FXML TableColumn<Force,String>  TroubleForcAdress;
    @FXML TableColumn<Force,String>  TroubleForcNote;

    private ObservableList<Trouble> troubleList;

    private void troubleInit(){
        troubleTable_Equipment.setCellValueFactory(new PropertyValueFactory<Trouble,Equipment>("equipment"));
        troubleTable_Data.setCellValueFactory(new PropertyValueFactory<Trouble,Date>("date"));
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
        troubleUpdateList();
    }
    private void troubleUpdateList(){
        troubleList = new Trouble().getData();
        troubleTable.setItems(troubleList);
    }

    private void troubleForseShowDetails(Force force){
        Lab_forceEng.setText(force.getTrouble().getEngineer().toString());
        Lab_forceDate.setText(force.getDate().toString());
        Lab_forcePLC.setText(force.getPlc().toString());
        Lab_forceAdr.setText(force.getAdress());
        Lab_forceInic.setText(force.getIniciator().toString());
        Lab_forceNote.setText(force.getNote());
        //Lab_forceStatus.setText(force.ge);
    }



    private void troubleShowDetails(Trouble trouble){
        trouble = troubleTable.getSelectionModel().getSelectedItem();
        if(trouble != null) {
            troubleForcesList = Force.getDataFiltered(trouble);
            TroubleForseTable.setItems(troubleForcesList);

            Lab_troubleZone.setText(trouble.getEquipment().getSubZone().getZone().toString());
            Lab_troubleEquipment.setText(trouble.getEquipment().toString());
            Lab_troubleNote.setText(trouble.getNote());
            Lab_troubleDate.setText(trouble.getDate().toString());
            Lab_troubleReason.setText(trouble.getReason());
            Lab_troubleActions.setText(trouble.getActions());
            Lab_troubleEngineer.setText(trouble.getEngineer().toString());

        }
    }

    @FXML void troubleAdd_Tap(){
        Trouble t = troubleTable.getSelectionModel().getSelectedItem();
        if(t == null) t = new Trouble();
        EditDialog E = new EditDialog(t);
        if(E.getRet()) {
            t.insert();
            troubleUpdateList();
        }
    }
    @FXML void troubleUpdate_Tap(){
        Trouble t = troubleTable.getSelectionModel().getSelectedItem();
        if(t == null) return;
        EditDialog E = new EditDialog(t);
        if(E.getRet()) {
            t.update();
            troubleUpdateList();
        }
    }

    @FXML void troubleDelete_Tap(){
        Trouble t = troubleTable.getSelectionModel().getSelectedItem();
        if(DeleteDialog(t)){
            troubleUpdateList();
        }
    }

    private<T extends dialogableModelDB> boolean DeleteDialog(T model){
        if(model == null){
            AlertInfo("Выберите " + "строку " + "перед удалением");
        }
        else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удалить");

            alert.setHeaderText("Подтвердите удаление " + model.getModelName());
            alert.setContentText(model.toString());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                // ... user chose OK
                model.delete();
                return true;
            } else {
                // ... user chose CANCEL or closed the dialog

            }
        }
        return false;
    }



    private void AlertInfo(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Предупреждение");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML void But_SQL_config_Tap(){
        Main.showSQLConfig_dialog();
        if(DBconnect()){
            DBreadDatas();
        }
    }
}


