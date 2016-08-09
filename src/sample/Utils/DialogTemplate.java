package sample.Utils;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.model.Row;

/**
 * Created by Иван on 06.07.2016.
 */
public class DialogTemplate {
    Stage stage;
    String title;       //dialogbox title
    Object model;
    GridPane gridPane;

    int numRow;

    public DialogTemplate(String title) {
        this.title = title;
    }

    public void start(){
        stage = new Stage();
        stage.setTitle(this.title);
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #336699;");
        // adding model content
        Text subtitle1 = new Text("title1");
        gridPane.add(subtitle1,0,0);
        Text subtitle2 = new Text("title2");
        gridPane.add(subtitle2,0,1);
        TextField TF_1 = new TextField();
        gridPane.add(TF_1,1,0);
        TextField TF_2 = new TextField();
        gridPane.add(TF_2,1,1);

        //adding Buttons
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);

        gridPane.add(hBox, 1, 2);
        Button But_OK = new Button("Сохранить");
        Button But_Cansel = new Button("Отменить");
        hBox.getChildren().addAll(But_OK, But_Cansel);

        stage.setScene(new Scene(gridPane,250,500));
        stage.show();
    }

//    public void addRow(String name, String val){
//        Row row = new Row(name, val);
//
//    }

    public void addRow(String name, Integer val){

    }

//    private void gridPaneAddNext(Row row){
//        Text txt = new Text();
//        gridPane.add(txt,0,Row.getPointer());
//        gridPane.add(row.getNode(),1,Row.getPointer());
//    }


}




class TempModel{
    Integer id;
    String name;
    String note;
}
