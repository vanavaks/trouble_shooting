package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import sample.model.Mothers.Column;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.Table;
import sample.model.Mothers.dialogableModelDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

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
    public boolean delete()throws SQLException{
            ObservableList<Instrument> i =  Instrument.getDataFiltered(this,null);
            if(i.size() == 0) return super.delete();
            return false;
    }
    public InstrumentFunc(String name, String note) {
        this(0,name,note);
        this.name = name;
        this.note = note;
    }
    public InstrumentFunc(){
        this(" "," ");
    }

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

    public static ObservableList<InstrumentFunc> getDataFiltered(Manufacturer manufacturer){
        ObservableList<InstrumentFunc> list = FXCollections.observableArrayList();
        try{
            Statement statement = getConnection().createStatement();
            String query =  "SELECT  " +
                            "A.id, " +
                            "A.name, " +
                            "A.note " +
                            "FROM troubleshooting.instrumentFunc  A " +
                            "INNER JOIN troubleshooting.instrument B " +
                            "ON(B.function_id = A.id) " +
                            " where B.manufacturer_id = " + manufacturer.getId();

//            String query = "SELECT * FROM troubleshooting.instrumentFunc " +
//                    " WHERE `A`.`engineer_id`=";
            query +=" order by name";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            Set<Integer> tempS = new HashSet<Integer>();
            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                Integer tempId =  resultSet.getInt("id");
                if(tempS.contains(tempId)) continue;
                tempS.add(tempId);
                InstrumentFunc IF =  new InstrumentFunc(  tempId,
                        resultSet.getNString("name"),
                        resultSet.getNString("note")
                );
                list.add(IF);
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
