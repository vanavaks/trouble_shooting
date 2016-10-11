package sample.FX.Trees;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import sample.FX.EditDialog;
import sample.FX.ITreePattern;
import sample.FX.InfoPane;
import sample.FX.Tree;
import sample.model.Instrument;
import sample.model.InstrumentFunc;
import sample.model.Manufacturer;
import sample.model.Mothers.dialogableModelDB;

import java.util.Optional;

/**
 * Created by VAN on 10.10.2016.
 */
public class MFITree extends Tree implements ITreePattern {

    public MFITree(String name) {
        super(name);
    }

    @Override
    public void buildMainTree() {
        ObservableList<Manufacturer> manufacturers = new Manufacturer().getData();
        buildSubTree(super.getRoot(),manufacturers);
    }
    /*
        @startuml
            start
            :**info**;
            :infoPaneRebuild(model);
            if(selTI have children?) then(yes)
                stop
            endif
            if(is Manufacturer?) then (yes)
                :**getFilteredFunctions**(Manufacturer);
            else if(is Functions?) then(yes)
                :**getFilteredInstuments**(Manufacturer, Functions);
            endif
            :**createTree**(selectedTI, list);
            stop
        @enduml
        */
    @Override
    public void info(TreeItem<? extends dialogableModelDB> selTreeItem){
        if(selTreeItem != null) {
            Object o = selTreeItem.getValue();
            if(o instanceof Manufacturer){
                Button addInstrumentBut = new Button("Добавить прибор");
                addInstrumentBut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        EditDialog<Instrument> ED = new EditDialog(new Instrument("","",(Manufacturer) o,null));
                        if (ED.getRet()) {
                            Instrument i = ED.getModel();
                            i.insert();
                            updateTreeItem(new TreeItem<Instrument>(i));
                        }
                    }
                });
                if(selTreeItem.getChildren().size()==0) {
                    ObservableList<InstrumentFunc> funcs = InstrumentFunc.getDataFiltered((Manufacturer)o);
                    buildSubTree(selTreeItem,funcs);
                }
                InfoPane.getInstance().rebuild(selTreeItem, this, addInstrumentBut);
            }else if(o instanceof Instrument) {
                InfoPane.getInstance().rebuild(selTreeItem, this);
            }else if(o instanceof InstrumentFunc){
                Button addInstrumentBut = new Button("Добавить прибор");
                addInstrumentBut.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        TreeItem o1 = selTreeItem.getParent();
                        EditDialog<Instrument> ED = new EditDialog(new Instrument("","",(Manufacturer) o1.getValue(),(InstrumentFunc)o));
                        if (ED.getRet()) {
                            Instrument i = ED.getModel();
                            i.insert();
                            updateTreeItem(new TreeItem<Instrument>(i));
                        }
                    }
                });
                if(selTreeItem.getChildren().size()==0) {
                    ObservableList<Instrument> instruments = Instrument.getDataFiltered((InstrumentFunc) selTreeItem.getValue(), (Manufacturer) selTreeItem.getParent().getValue());
                    buildSubTree(selTreeItem, instruments);
                }
                InfoPane.getInstance().rebuild(selTreeItem, this, addInstrumentBut);
            }
        }
    }

    @Override
    public void removeChildrens(TreeItem treeItem){
        if (treeItem == null) return;
        if (treeItem.getValue() == null) return;
        ObservableList<TreeItem> treeItemList = treeItem.getChildren();
        if (treeItemList != null & treeItemList.size()>0){
            if(treeItemList.get(0).getValue() instanceof Instrument) return;
            treeItemList.stream().forEach(item -> {
                removeChildrens(item);
            });
        }
        treeItem.getChildren().clear();
    }



    /*
            @startuml
                start
                :**updateTreeItem**;
                :removeChildrens;
                :temp = treeItem.getValue;
                if (TreeItem is root?) then(yes)
                    :**buildMainTree**();
                    stop
                else
                if(is Manufacturer?) then (yes)
                    :**getFilteredFunctions**(Manufacturer);
                    :**createTree**(selectedTI, list);
                else if(is Functions?) then(yes)
                else if(is Instruments) then(yes)
                    :**getManufacturers**();
                    :**createTree**(rootTI, list);
                    :**getFunctions**(selManufacturer);
                    :**createTree**(retTI, list);
                    :**getFilteredInstuments**(Manufacturer, Functions);
                    :**createTree**(selectedTI, list);
                endif
                stop
            @enduml
            */
    @Override
    public<M extends dialogableModelDB> void updateTreeItem(TreeItem<M> rootTI) {
        if(rootTI == null) return;
        M model = rootTI.getValue();
        if(model == null) return;
        if(model instanceof RootModel){
            removeChildrens(rootTI);
            buildMainTree();
        } else if (model instanceof Manufacturer){
            removeChildrens(super.getRoot());
            buildMainTree();
           /* TreeItem<Manufacturer> tim = null;
            rootTI.setValue((M)Manufacturer.get(rootTI.getValue().getId()));
            ObservableList<InstrumentFunc> funcs = InstrumentFunc.getDataFiltered((Manufacturer)tim.getValue());
            buildSubTree(tim,funcs);*/
        }else if (model instanceof InstrumentFunc){

        }else if(model instanceof Instrument){
            removeChildrens(super.getRoot());
            ObservableList<Manufacturer> manufacturerList = new Manufacturer().getData();
            buildSubTree(super.getRoot(),manufacturerList);
            TreeItem<Manufacturer> tim = null;
            TreeItem<InstrumentFunc> tif = null;
            Optional m = super.getRoot().getChildren().stream().filter(item ->
                    item.getValue().getId() == ((Instrument)model).getManufacturer().getId()).findFirst();
            if(m.isPresent()){
                tim = (TreeItem) m.get();
            }
            ObservableList<InstrumentFunc> funcs = InstrumentFunc.getDataFiltered((Manufacturer)tim.getValue());
            buildSubTree(tim,funcs);
            Integer n = ((Instrument)model).getFunction().getId();
            for(TreeItem item: tim.getChildren()){
                InstrumentFunc If = (InstrumentFunc)(item.getValue());
                if(If.getId() == n){
                    ObservableList<Instrument> instruments = Instrument.getDataFiltered(If, tim.getValue());
                    buildSubTree(item,instruments);
                }
            }
        }
    }
}
