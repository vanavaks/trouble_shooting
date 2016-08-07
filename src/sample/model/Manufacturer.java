package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Иван on 16.07.2016.
 */
@Table(title = "Функция прибора", table = "manufacturer")
public class Manufacturer extends dialogableModelDB {
    @Column(title = "Производитель", column = "name")
    @NotNull(message = "Введите название производителя")
    public String name;
    @Column(title = "Примечание", column = "note")
    public String note;

    public Manufacturer(String name, String note) {
        this(0,name,note);
    }
    public Manufacturer(){
        this(0,"","");
    }
    public Manufacturer(int id, String name, String note){
        this.name = name;
        this.note = note;
        super.setId(id);
    }

    public static Manufacturer get(int id){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.manufacturer " +
                            "where id = " + id + ";"
            );
            if (resultSet.next()) {
                return
                        new Manufacturer(  resultSet.getInt("id"),
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
    public static Manufacturer get(){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.manufacturer " +
                            " order by id asc limit 1 ;"
            );
            if (resultSet.next()) {
                return
                        new Manufacturer(  resultSet.getInt("id"),
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

    public ObservableList<Manufacturer> getData(){

        ObservableList<Manufacturer> list = FXCollections.observableArrayList();
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.manufacturer "
            );
            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                list.add(
                        new Manufacturer(  resultSet.getInt("id"),
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
    @Override
    public void delete(){
        ObservableList<Instrument> i =  Instrument.getDataFiltered(null, this);
        if(i.size() != 0) return;
        //if(sz.size() > 0) return;
        super.delete();
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
