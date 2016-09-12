package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;
import java.util.logging.Logger;

/**
 * Created by VAN on 23.08.2016.
 */
public class InfoPane <M extends dialogableModelDB> extends Pane{
    private GridPane gridPane;
    private TableView<?> table;
    private Label title;
    private VBox vbox;
    private int size;
    private HashSet<Node> NodeSet;

    private double length = 200;

    Logger log = Logger.getLogger(InfoPane.class.getName());
    public InfoPane() {
        this(null);
    }
    public InfoPane(M model) {
        NodeSet = new HashSet<>();
        vbox = new VBox();
        title = new Label();
        vbox.getChildren().add(title);
        gridPane = new GridPane();

            //title
        if (model != null) build(model);
        vbox.getChildren().add(gridPane);
        super.getChildren().add(vbox);
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
        for(Node node: NodeSet){
            gridPane.getChildren().remove(node);
        }
        gridPane.getChildren().removeAll();
    }
    public void build(M model){
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

    class tableItem{
        String field;
        Node item;

        public tableItem(String field, Node item) {
            this.field = field;
            this.item = item;
        }
        public String getField() {
            return field;
        }
        public void setField(String field) {
            this.field = field;
        }
        public Node getItem() {
            return item;
        }
        public void setItem(Node item) {
            this.item = item;
        }
    }

    public void _build(M model){
        Class<?> c = model.getClass();
        Table t = c.getAnnotation(Table.class);
        String s = t.title();
        title.setText(s);
        Field[] fs = c.getFields();
        ObservableList<?> list = FXCollections.observableArrayList();
        //table.setItems(list);

        for(int i=0;fs.length>i; i++){
            //add label
            Column column = fs[i].getAnnotation(Column.class);
            Label label = new Label(column.title());
            gridPane.add(label, 0, i);
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
                    //node = new Button();

                }
                gridPane.add(valNode, 1, i);
            } catch (IllegalAccessException e) {
                log.throwing(this.getClass().toString(),"InfoPane_constructor",e);
            }
        }
    }
    public void rebuild(M model){
        removeElements();
        build(model);
    }
}
