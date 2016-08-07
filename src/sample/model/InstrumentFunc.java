package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Иван on 16.07.2016.
 */

@Table(title = "Функция прибора", table = "instrumentFunc")
public class InstrumentFunc extends dialogableModelDB {
    @Column(title = "Функция", column = "name")
    @NotNull(message = "Введите функцию")
    //@Size(min = 5, message = "Слишком малый размер")
    public String name;

    @Column(title = "Примечание", column = "note")
    public String note;

    public InstrumentFunc(int id, String name, String note) {
        super.setId(id);
        this.name = name;
        this.note = note;
    }

    @Override
    public void delete(){
            ObservableList<Instrument> i =  Instrument.getDataFiltered(this,null);
            if(i.size() != 0) return;
            //if(sz.size() > 0) return;
            super.delete();
    }
    public InstrumentFunc(String name, String note) {
        this(0,name,note);
        this.name = name;
        this.note = note;
    }
    public InstrumentFunc(){}
    public static InstrumentFunc get(int id){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.instrumentFunc " +
                            "where id = " + id + ";"
            );
            if (resultSet.next()) {
                return
                        new InstrumentFunc(  resultSet.getInt("id"),
                                resultSet.getNString("name"),
                                resultSet.getNString("note")
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static InstrumentFunc get(){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.instrumentFunc " +
                            " order by id asc limit 1 ;"
            );
            if (resultSet.next()) {
                return
                        new InstrumentFunc(  resultSet.getInt("id"),
                                resultSet.getNString("name"),
                                resultSet.getNString("note")
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public ObservableList<InstrumentFunc> getData(){

        ObservableList<InstrumentFunc> list = FXCollections.observableArrayList();
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.instrumentFunc "
            );
            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                list.add(
                        new InstrumentFunc(  resultSet.getInt("id"),
                                resultSet.getNString("name"),
                                resultSet.getNString("note")
                        )

                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return name;
    }
}
