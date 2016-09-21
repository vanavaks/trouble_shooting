package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import sample.Controller;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.logging.Logger;

/**
 * Created by VAN on 23.08.2016.
 */
public class InfoPane <M extends dialogableModelDB> extends Pane{
    private static Logger log = Logger.getLogger(dialogableModelDB.class.getName());
    private GridPane gridPane;
    private Label title;
    private VBox vbox;
    private Label crudTitle;
    private HBox crudBox;
    private HashSet<Node> NodeSet, crudSet;
    private M model = null;

    private double length = 200;

    public InfoPane() {
        this(null);
    }
    public InfoPane(M model) {
        this.model = model;
        NodeSet = new HashSet<>();
        crudTitle = new Label("Редактирование");
        crudSet = new HashSet<>();
        vbox = new VBox();
        crudBox = new HBox();
        title = new Label();
        vbox.getChildren().add(title);
        gridPane = new GridPane();
        vbox.getChildren().add(gridPane);
        super.getChildren().add(vbox);


            //title
        if (model != null) {
            infoBuild(model);
            crudBuild();
        }
    }
    private Node createIntNode(Integer val) {
        Label label = new Label(val.toString());
        return label;
    }
    private Node createBoolNode(Boolean val) {
        CheckBox cb = new CheckBox();
        cb.setSelected(val);
        cb.setDisable(true);
        return cb;
    }
    private <L extends dialogableModelDB> Node createModelNode(L mdl) {
        Label label = new Label(mdl.toString());
        return label;
    }
    private Node createDateNode(Date val) {
        Label label = new Label(val.toString());
        return label;
    }
    private Node createStrNode(String str) {
        Label label = new Label(str);
        return label;
    }

    public void removeElements(){
        title.setText("");
//        for(int i=0;i<size;i++){
//            gridPane.getChildren()
//        }
//        for(Node c :gridPane.getChildren()){
//            gridPane.getChildren().
//        }
            //remove info
        for(Node node: NodeSet){
            gridPane.getChildren().remove(node);
        }
        NodeSet.clear();
        gridPane.getChildren().removeAll();
            //removing crud box
        for(Node node: crudSet){
            crudBox.getChildren().remove(node);
        }
        crudSet.clear();
        crudBox.getChildren().removeAll();
        vbox.getChildren().remove(crudTitle);
        vbox.getChildren().remove(crudBox);
    }

    private void crudBuild(){
        vbox.getChildren().add(crudTitle);
        vbox.getChildren().add(crudBox);
        Button create = new Button("новый");
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditDialog<M> ED= new EditDialog(model.clone());
                if(ED.getRet()) {
                    ED.getModel().insert();
                }
            }
        });
        Button update = new Button("изменить");
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EditDialog<M> ED= new EditDialog(model);
                if(ED.getRet()) {
                    ED.getModel().update();
                }
            }
        });
        Button delete = new Button("удалить");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                model.delete();
            }
        });
        crudBox.setPadding(new Insets(15, 12, 15, 12));
        crudBox.setSpacing(10);
        crudBox.getChildren().addAll(create, update, delete);
        crudSet.add(create);
        crudSet.add(update);
        crudSet.add(delete);
    }

    public void infoBuild(M model){
        this.model = model;
        Class<?> c = model.getClass();
        Table t = c.getAnnotation(Table.class);
        String s = t.title();
        title.setText(s);
        Field[] fs = c.getFields();
        for(int i=0;fs.length>i; i++){
            //add label
            Column column = fs[i].getAnnotation(Column.class);
            Label label = new Label(column.title());
            gridPane.add(label, 0, i);
            NodeSet.add(label);
            //add value
            try {
                Object o = fs[i].get(model);
                Node valNode = null;
                if(o instanceof String){
                    valNode = createStrNode((String) o);
                }
                else if(o instanceof java.util.Date){
                    valNode = createDateNode((java.util.Date)o);
                }
                else if(o instanceof dialogableModelDB){
                    valNode = createModelNode((dialogableModelDB) o);
                }
                else if (o instanceof Integer){
                    valNode = createIntNode((Integer)o);
                }
                else if (o instanceof Boolean){
                    valNode = createBoolNode((Boolean)o);
                }
                else{
                    //valNode = new Button();
                }
                gridPane.add(valNode, 1, i);
                NodeSet.add(valNode);
            } catch (IllegalAccessException e) {
                log.throwing(this.getClass().toString(),"InfoPane_constructor",e);
            } catch (Exception e) {
                log.throwing(this.getClass().toString(),"InfoPane_constructor",e);
            }
        }
    }

    public void rebuild(M model){
        removeElements();
        infoBuild(model);
        crudBuild();
    }
}
