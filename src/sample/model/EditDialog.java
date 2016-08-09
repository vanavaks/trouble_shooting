package sample.model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Иван on 06.07.2016.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface Column{
    String title();
    String column();
}

@Retention(RetentionPolicy.RUNTIME)
@interface EditedFields {
    String[] value() default {};
}

@Retention(RetentionPolicy.RUNTIME)
@interface Table {
    String title();
    String table();
}

@Retention(RetentionPolicy.RUNTIME)
@interface NotNull{
    String message();
}
public class EditDialog<T extends dialogableModelDB> {
    private VBox vbox;
    private Stage stage;
    private List<Row> rowList = new ArrayList<Row>();  //rows list
    private T model, oldModel;
    private Button But_OK;
    private Boolean ret = false;

//    public EditDialog(String title, T model ) {
//        vbox = new VBox();
//        this.oldModel = model;
//        buildDialog(model.getVarMap(), title);
//        stage.setScene(new Scene(vbox));
//        stage.showAndWait();
//    }

    public EditDialog(T model){
        vbox = new VBox();
        this.model = model;
        buildDialog(model);
        stage.setScene(new Scene(vbox));
        stage.showAndWait();
    }
    public void buildDialog(T model){
        stage = new Stage();
        Class<?> c = model.getClass();
        Table table = c.getAnnotation(Table.class);
        String s = table.title();
        System.out.println(s);
        stage.setTitle(s);
        vbox.setStyle("-fx-background-color: #336699;");//#336699
            //Rows
        Field[] fs= c.getFields();
        if(fs == null) {
            vbox.getChildren().add(new Label("Не найдено ни одного редактируемого поля"));
            return;
        }
        for (Field f: c.getFields()) {
            addRow(new Row(f,model));
//                    try {
//                        Column column = f.getAnnotation(Column.class);
//                        Object o = f.get(model); //.toString();
//                        String title = column.title();
//                        if(o instanceof String){
//                            Row r = new Row(title, (String) o);
//                            r.setField(f.getName());
//                            r.setF(f);
//                            addRow(r);
//                        }
//                        else if(o instanceof java.util.Date){
//                            Row r = new Row(title, (java.util.Date) o);
//                            r.setField(f.getName());
//                            addRow(r);
//                        }
//                        else if(o instanceof dialogableModelDB){
//                            Row<T> r = new Row(title, (T)o, ((T) o).getData());
//                            r.setField(f.getName());
//                            addRow(r);
//                        }
//                        else if (o instanceof Integer){
//                            Row r = new Row(title, (Integer) o);
//                            r.setField(f.getName());
//                            r.setF(f);
//                            addRow(r);
//                        }
//                        else if (o instanceof Boolean){
//                            Row r = new Row(title, (Boolean) o);
//                            r.setField(f.getName());
//                            r.setF(f);
//                            addRow(r);
//                        }
//                    } catch (IllegalAccessException e) {e.printStackTrace();}
        }
            //Buttons
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        But_OK = new Button("Сохранить");
        But_OK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Row r : rowList) {
                    if(!r.isValid()) return;
                }
//                            if (r.getNode() instanceof TextField) {
//
////                                Field f = c.getField(r.getField());;
////                                if(f.get(model)instanceof Integer){
////                                    f.set(model,Integer.parseInt((String)value));
////                                }
//                                setField(c, r.getField(), ((TextField) r.getNode()).getText());
//                            } else if (r.getNode() instanceof ComboBox) {
//                                setField(c, r.getField(), ((ComboBox) r.getNode()).getSelectionModel().getSelectedItem());
//                            } else if (r.getNode() instanceof DatePicker) {
//                                LocalDate localDate = ((DatePicker) r.getNode()).getValue();
//                                java.util.Date javaDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth() /*localDate.toEpochDay()*/);
//                                setField(c, r.getField(), javaDate);
//                            }
//                            else if (r.getNode() instanceof CheckBox){
//                                setField(c, r.getField(), ((CheckBox) r.getNode()).isSelected());
//                            }
//                        }
                ret = true;
                stage.close();
            }
        });
        Button But_Cansel = new Button("Отменить");
        But_Cansel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ret = false;
                stage.close();
            }
        });
        hBox.getChildren().addAll(But_OK, But_Cansel);
        vbox.getChildren().add(hBox);
    }

    public void addRow(Row row){
        rowList.add(row);
        Separator s = new Separator();
        this.vbox.getChildren().addAll(row.getBox(), s);
    }


//
//    public void buildDialog(Map<String, Object> map, String title) {
//        stage = new Stage();
//        stage.setTitle(title);
//        vbox.setStyle("-fx-background-color: #336699;");
//
//        //Rows
//        if (model.getVarMap() != null) {
//            for (Map.Entry<String, Object> e : model.getVarMap().entrySet()) {
//                if (e.getValue() instanceof String) {
//                    Row r = new Row(e.getKey(), (String) e.getValue());
//                    addRow(r);
//                }
//                else if(e.getValue() instanceof Zone){
//                    Zone z = new Zone();
//                    Row<Zone> r = new Row<Zone>(e.getKey(), (Zone)e.getValue(), z.getData());
//                    addRow(r);
//                }
//                else if(e.getValue() instanceof PLC){
//                    PLC p = new PLC();
//                    Row<PLC> r = new Row<PLC>(e.getKey(),(PLC)e.getValue(), p.getData());
//                    addRow(r);
//                }
//                else if(e.getValue() instanceof Engineer){
//                    Engineer en = new Engineer();
//                    Row<Engineer> r = new Row<Engineer>(e.getKey(),(Engineer)e.getValue(), en.getData());
//                    addRow(r);
//                }
//                else if(e.getValue() instanceof Iniciator){
//                    Iniciator inic = new Iniciator();
//                    Row<Iniciator> r = new Row<Iniciator>(e.getKey(),(Iniciator)e.getValue(), inic.getData());
//                    addRow(r);
//                }
//                else if(e.getValue() instanceof java.util.Date){
//                    Row r = new Row(e.getKey(), (java.util.Date)e.getValue());
//                    addRow(r);
//                }
////                else if (e.getValue() instanceof T){
////                    //Row<T> r = new Row<T>(e.getKey(), (T)e.getValue(), T.getData());
////                }
//            }
//
//        }
//        //Buttons
//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(15, 12, 15, 12));
//        hBox.setSpacing(10);
//        Button But_OK = new Button("Сохранить");
//        But_OK.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                for (Row<Node> r : rowList) {
//                    if (r.getNode() instanceof TextField) {
//                        model.getVarMap().put(r.getName(), ((TextField) r.getNode()).getText());
//                    } else if (r.getNode() instanceof ComboBox) {
//                        model.getVarMap().put(r.getName(), ((ComboBox) r.getNode()).getSelectionModel().getSelectedItem());
//                    }
//                    else if (r.getNode() instanceof DatePicker) {
//                        LocalDate localDate = ((DatePicker) r.getNode()).getValue();
//                        java.util.Date javaDate = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth() /*localDate.toEpochDay()*/);
//
//                        model.getVarMap().put(r.getName(), javaDate);
//                    }
//
//                }
//                //model.updateFromMap();
//                ret = true;
//                stage.close();
//            }
//        });
//        Button But_Cansel = new Button("Отменить");
//        hBox.getChildren().addAll(But_OK, But_Cansel);
//        But_Cansel.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                stage.close();
//                ret = false;
//            }
//        });
//        vbox.getChildren().add(hBox);
//    }


//    private void setField(Class<?> c, String fieldName, Object value){
//        try {
//            Field f = c.getField(fieldName);
//            if(f.get(model)instanceof Integer){
//                f.set(model,Integer.parseInt((String)value));
//            }
//            else{
//                f.set(model,value);
//            }
//        } catch (NoSuchFieldException e) {
//            System.out.println("Поле не найдено");
//            e.printStackTrace();
//        }
//        catch (IllegalAccessException e){
//            System.out.println("Поле не возможно записать");
//            e.printStackTrace();
//        }
//    }

    public T getModel() {
        return model;
    }
    public Boolean getRet() {
        return ret;
    }

    //    public void showDialog() {
//        stage = new Stage();
//
//        //ObservableList<Zone> zone;
//        //zone = Zone.getZonesData();
//
//        stage.setTitle(this.title);
//        vbox.setStyle("-fx-background-color: #336699;");
//        // adding model content
//        //this.addRow("title1", "value1");
//        //this.addRow("tit", "val");
//        //this.addRow("list", zone);
//
//        //adding Buttons
//        HBox hBox = new HBox();
//        hBox.setPadding(new Insets(15, 12, 15, 12));
//        hBox.setSpacing(10);
//        Button But_OK = new Button("Сохранить");
//        But_OK.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                for (Row<Node> r : rowList) {
//                    r.getValue();
////                    String  s = r.getValue().getClass().getTypeName();
////                    System.out.println(s);
////                    switch (s){
////                        case "java.lang.String" :{
////                            TextField tf = (TextField) r.getNode();
////                            System.out.println(tf.getText());
////                        }
////                        case "list" :{
////                            ;
////                        }
////                        default : break;
////                    }
//
//
////                    System.out.println(r.getNode().getClass().getName());
////                    String type = r.getNode().getClass().getName();
////                    if(type == "javafx.scene.control.TextField"){
////                        TextField tf = (TextField) r.getNode();
////                        tf.getText();
////
////                    }
//                }
//                for (Row<Node> r : rowList) {
//
//                }
//            }
//        });
//        Button But_Cansel = new Button("Отменить");
//        hBox.getChildren().addAll(But_OK, But_Cansel);
//
//        vbox.getChildren().add(hBox);
//        stage.setScene(new Scene(vbox));
//        stage.showAndWait();
//    }
//
//    public void addRow(String title, String value) {
//        Row r = new Row(title, value);
//        rowList.add(r);
//        this.vbox.getChildren().add(r.getBox());
//    }
//
//    public void addRow(String title, ObservableList list) {
//        Row r = new Row(title, list);
//        rowList.add(r);
//        this.vbox.getChildren().add(r.getBox());
//    }

}

