package sample.model;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Иван on 06.07.2016.
 */
public class Row<T> {
    private String field;
    private String name;
    private int n;  //number of properties
    private Node node;
    private HBox box;     //выравнивание столбцов нет
    private Label validator;
    private Field f;
    private static final double spacing = 100;
    private boolean validationOk = true;
//    public Row(String name, Node node) {
//        box = new HBox(spacing);
//        this.name = name;
//        this.node = node;
//    }
//    public Row(String name, ObservableList<T> list){
//        box = new HBox(spacing);
//        this.name = name;
//        Text text = new Text(name);
//        ComboBox<T> cb = new ComboBox<T>();
//        cb.setItems(list);
//        cb.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println(cb.getSelectionModel().getSelectedItem());
//            }
//        });
//        this.node = cb;
//        this.addRow(text, cb);
//    }

    public Row(String name, Node editor){
        box = new HBox(spacing);
        //this.name = name;
        this.node = editor;
        Text text = new Text(name);
        validator = new Label("");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(editor, validator);
        box.getChildren().addAll(text, vbox);
    }
    public Row(String name, Node editor,String fieldName){
        this(name, editor);
        this.field = fieldName;
    }
    public Row(String name, T model, ObservableList<T> list, String fieldName){
        this(name, new ComboBox<T>(),fieldName);
        ((ComboBox)this.node).setValue(model);
        ((ComboBox)this.node).setItems(list);
    }

    public Row(String name, String val) {
//        box = new HBox(spacing);
//        this.name = name;
//        this.value = val;
//        Text text = new Text(name);
//        TextField tf = new TextField(val);
//        this.node = tf;
//        this.addRow(text, tf);
        this(name, new TextField(val));
       TextField tf = (TextField)(this.node);
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent InputMethodEvent) {

                NotNull nnAnot = f.getAnnotation(NotNull.class);
                if(f.isAnnotationPresent(NotNull.class)){
                    if (tf.getText().equals("")){
                        validator.setText(nnAnot.message());
                        validator.setStyle("-fx-text-fill:red");
                        validationOk = false;
                    }
                    else{
                        validator.setText("");
                        validator.setStyle("-fx-text-fill:green");
                        validationOk = true;
                    }
                    //ButOK_chackDisable();
                }
                //validate(this.field);
            }
        });
    }
    public Row(String name, T model, ObservableList<T> list){
//        box = new HBox(spacing);
//        this.name = name;
//        Text text = new Text(name);
//        ComboBox<T> cb = new ComboBox<T>();
//        cb.setValue(model);
//        cb.setItems(list);
//        this.node = cb;
//        this.addRow(text, cb);


//        ComboBox<T> cb = new ComboBox<T>();
//        cb.setValue(model);
//        cb.setItems(list);
        this(name, new ComboBox<T>());
        ((ComboBox)this.node).setValue(model);
        ((ComboBox)this.node).setItems(list);
    }
    public Row(String name, Date date){
        this(name, new DatePicker());
        Instant instant = date.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        ((DatePicker)this.node).setValue(localDate);
    }

    public Row(String name, Boolean val){
        this(name, new CheckBox());
        ((CheckBox)this.node).setSelected(val);
    }
     private boolean isValidInt(String s){
         String regex = "[0-9]+";
         if(s.equals(""))return true;
         return s.matches(regex);
     }

    public Row(String name, Integer val){
        this(name, new TextField(val.toString()));
        TextField tf = (TextField)(this.node);
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                //String s="";

//                byte[] bs = tf.getText().getBytes();
//                for(int i=0;i<bs.length;i++){
//                    if(bs[i] < '9' & bs[i]>'0' | bs[i] != '.'){
//                        s += bs[i];
//                    }
//                }
//                tf.setText(s);
                validator.setStyle("-fx-text-fill:green");
                validator.setText("OK");
                if(!isValidInt(tf.getText())){
                    validator.setStyle("-fx-text-fill:red");
                    validator.setText("Допустимы только целые числа");
                }

                NotNull nnAnot = f.getAnnotation(NotNull.class);
                if(f.isAnnotationPresent(NotNull.class)) {
                    if (tf.getText().equals("")) {
                        validator.setStyle("-fx-text-fill:red");
                        validator.setText(nnAnot.message());
                    }

                    //ButOK_chackDisable();
                }
                //validate(this.field);
            }
        });
    }
//    public String getValue(TextField tf){
//        this.setValue(tf.getText());
//        return tf.getText();
//    }
//    public T getValue(ComboBox<T> cb){
//        this.setValue(cb.getValue());
//        return cb.getValue();
//    }
//    private void addRow(Node name, Node editor){
//        box.getChildren().addAll(name, editor);
//    }

    public Node getNode() {
        return node;
    }
    public Pane getBox() {
        return box;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Field getF() {
        return f;
    }

    public void setF(Field f) {
        this.f = f;
    }

    public boolean isValidationOk() {
        return validationOk;
    }

    public void setValidationOk(boolean validationOk) {
        this.validationOk = validationOk;
    }
}
