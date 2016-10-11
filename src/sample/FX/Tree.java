package sample.FX;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import sample.model.Instrument;
import sample.model.InstrumentFunc;
import sample.model.Manufacturer;
import sample.model.Mothers.dialogableModelDB;
import sample.model.SubZone;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by VAN on 05.10.2016.
 */
public class Tree {
    public enum TreeDot{
        MANUFACTURER(new Manufacturer()), FUNCTION(new InstrumentFunc()), INSTRUMENT(new Instrument());
        //private M model;
        <M extends dialogableModelDB> TreeDot(M model){

        }
    }

    public enum TreePattern
    {
        MFI(TreeDot.MANUFACTURER, TreeDot.FUNCTION, TreeDot.INSTRUMENT),
        FMI(TreeDot.FUNCTION,TreeDot.MANUFACTURER,TreeDot.INSTRUMENT);

        private ArrayList<TreeDot> levels;

        TreePattern(TreeDot ... args){
            for(TreeDot dot: args){
                levels.add(dot);
            }
        }
    }


    protected class RootModel extends dialogableModelDB{
        private String name;

        public RootModel(String name) {
            this.name = name;
        }

        @Override
        public ObservableList<?> getData() {
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private TreeItem<dialogableModelDB> root;
    public TreeItem<dialogableModelDB> getRoot() {
        return root;
    }
    public void setRoot(TreeItem root) {
        this.root = root;
    }




    private dialogableModelDB tempModel;
    public dialogableModelDB getTempModel() {
        return tempModel;
    }
    public void setTempModel(dialogableModelDB tempModel) {
        this.tempModel = tempModel;
    }


    public Tree(String name){
        this.setRoot(new TreeItem(new RootModel(name)));
        this.getRoot().setExpanded(true);
    }


    /*
    @startuml
    :**build**;
    :**createFirstList**|
    @enduml
    */
    public void build(TreeItem subRootItem){
        createFirstList();
    }

    private void createFirstList(){

    }



    public <M extends dialogableModelDB> void updateTreeItem(TreeItem<M> rootTI){

    }
    public <M extends dialogableModelDB> void buildSubTree(TreeItem<? extends dialogableModelDB> rootItem, ObservableList<M> list) {
        TreeItem temp;
        list.stream().forEach(item ->{
            TreeItem treeItem = new TreeItem(item);
            rootItem.getChildren().add(treeItem);
            treeItem.setExpanded(true);
        });
    }

    public void removeChildrens(TreeItem treeItem){
        if (treeItem == null) return;
        if (treeItem.getValue() == null) return;
        ObservableList<TreeItem> treeItemList = treeItem.getChildren();
        if (treeItemList != null & treeItemList.size()>0){
            treeItemList.stream().forEach(item -> {
                removeChildrens(item);
            });
        }
        treeItem.getChildren().clear();
    }
    public void updateParrentTI(TreeItem treeItem){
        if(treeItem.equals(this.getRoot())) return;
        this.updateTreeItem(treeItem.getParent());
    }

}
