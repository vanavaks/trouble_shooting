package sample.model;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
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
 * Revision 0.2 08.08.2016
 * - Validation in external class
 * - All changes get in event handlers
 * - Class work with models fields
 */
public class Row<T extends dialogableModelDB> {
    private Node node;
    private HBox box;     //выравнивание столбцов нет
    private Label validText;
    private Field f;
    private boolean isValid = true;
    private T model;
    public ObservableList list;
    private static final double spacing = 100;

    public Row(Field f, T model){
        this.setF(f);
        this.setModel(model);
        Column column = f.getAnnotation(Column.class);
        validText = new Label("");
        box = new HBox(spacing);
        Text text = new Text(column.title());
        VBox vbox = new VBox();

        box.getChildren().addAll(text, vbox);

        try {
            Object o = f.get(model);
            if(o instanceof String){
                createStrRow((String) o);
            }
            else if(o instanceof java.util.Date){
                createDateRow((java.util.Date)o);
            }
            else if(o instanceof dialogableModelDB){
                createModelRow((dialogableModelDB) o);
            }
            else if (o instanceof Integer){
                createIntRow((Integer)o);
            }
            else if (o instanceof Boolean){
                createBoolRow((Boolean)o);
            }
            else{
                node = new Button();
            }
        } catch (IllegalAccessException e) {
            System.out.println("--can't find model field--");
            e.printStackTrace();
        }
            //visual component creation
        vbox.getChildren().addAll(this.node, validText);
    }

    private void createIntRow(Integer val) {
        TextField tf = new TextField(val.toString());
        this.node = tf;

        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Class<?> c = model.getClass();
                try {
                    if(Validator.isValidInt(tf.getText())){
                        f.set(model,Integer.parseInt(tf.getText()));
                        String s = Validator.validate(f,model);
                        setValid(s);
                    }
                    else{
                        setValid("Допустимы только целые числа");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
//
//
//                NotNull nnAnot = f.getAnnotation(NotNull.class);
//                if(f.isAnnotationPresent(NotNull.class)) {
//                    if (tf.getText().equals("")) {
//                        validText.setStyle("-fx-text-fill:red");
//                        validText.setText(nnAnot.message());
//                    }
//                }
            }
        });
    }


    private void createBoolRow(Boolean val) {
        CheckBox cb = new CheckBox();
        this.node = cb;
        cb.setSelected(val);
        cb.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                Class<?> c = model.getClass();
                try {
                    f.set(model,cb.isSelected());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private <L extends dialogableModelDB> void createModelRow(L mdl) {
        ComboBox<L> cmb = new ComboBox<L>();
        this.node = cmb;
        cmb.setValue(mdl);
        list = mdl.getData();
        cmb.setItems(list);

        cmb.setOnHidden(new EventHandler<Event>(){
            @Override
            public void handle(Event event) {
                try {
                    L m = cmb.getSelectionModel().getSelectedItem();
                    f.set(model,m);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button = new Button("Новый");
        button.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {

                EditDialog<T> E = new EditDialog(mdl);
                mdl.insert();
                //updateList(mdl);
                list = mdl.getData();
                cmb.setItems(list);
                cmb.setValue(mdl);    //нужно получить ID из базы данных
            }
        });

        box.getChildren().add(button);
    }

    private void updateList(dialogableModelDB mdl){
        list = mdl.getData();
    }

    private void createDateRow(Date val) {
        DatePicker dp = new DatePicker();
        Class<?> c = model.getClass();
        //setValid(Validator.validate(f,c));
        this.node = dp;
        Instant instant = val.toInstant();
        LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        dp.setValue(localDate);

        dp.setOnHidden(new EventHandler<Event>(){
            @Override
            public void handle(Event event) {
                try {
                    LocalDate localDate = dp.getValue();
                    java.util.Date date = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth() /*localDate.toEpochDay()*/);
                    System.out.println(date);
                    f.set(model,date);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createStrRow(String str) {
        TextField tf = new TextField(str);
        this.node = tf;
            //Validation
        setValid(Validator.validate(f,model));

            //Text change event handler
        tf.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent InputMethodEvent) {
                    //Update field
                try {
                    f.set(model,tf.getText());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                    //Validation
                String s = Validator.validate(f,model);
                setValid(s);
            }
        });
    }

    private void setValid(String s){
        if(s != null | s == ""){
            validText.setText(s);
            validText.setStyle("-fx-text-fill:red");
            isValid = false;
        }
        else{
            validText.setText("OK");
            validText.setStyle("-fx-text-fill:green");
            isValid = true;
        }
    }
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

   /* public Row(String name, Node editor){
        box = new HBox(spacing);
        //this.name = name;
        this.node = editor;
        Text text = new Text(name);
        validText = new Label("");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(editor, validText);
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
                        validText.setText(nnAnot.message());
                        validText.setStyle("-fx-text-fill:red");
                        isValid = false;
                    }
                    else{
                        validText.setText("");
                        validText.setStyle("-fx-text-fill:green");
                        isValid = true;
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
                validText.setStyle("-fx-text-fill:green");
                validText.setText("OK");
                if(!isValidInt(tf.getText())){
                    validText.setStyle("-fx-text-fill:red");
                    validText.setText("Допустимы только целые числа");
                }

                NotNull nnAnot = f.getAnnotation(NotNull.class);
                if(f.isAnnotationPresent(NotNull.class)) {
                    if (tf.getText().equals("")) {
                        validText.setStyle("-fx-text-fill:red");
                        validText.setText(nnAnot.message());
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
*/

    public Node getNode() {
        return node;
    }
    public Pane getBox() {
        return box;
    }
    public Field getF() {
        return f;
    }
    public void setF(Field f) {
        this.f = f;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setModel(T model) {
        this.model = model;
    }
}
