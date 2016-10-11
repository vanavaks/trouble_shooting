package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
@Table(title = "Производитель", table = "manufacturer")
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
                            "FROM troubleshooting.manufacturer " +
                            " order by name "
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
    public boolean delete()throws SQLException{
        ObservableList<Instrument> i =  Instrument.getDataFiltered(null, this);
        if(i.size() == 0) return super.delete();
        return false;
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

    public static ObservableList<Manufacturer> getDataFiltered(InstrumentFunc instrumentFunc) {
        ObservableList<Manufacturer> list = FXCollections.observableArrayList();
        try{
            Statement statement = getConnection().createStatement();
            String query =  "SELECT  " +
                    "A.id, " +
                    "A.name, " +
                    "A.note " +
                    "FROM troubleshooting.manufacturer  A " +
                    "INNER JOIN troubleshooting.instrument B " +
                    "ON(B.manufacturer_id = A.id) " +
                    " where B.instrumentfunc_id = " + instrumentFunc.getId();
            query +=" order by name";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            Set<Integer> tempS = new HashSet<Integer>();
            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                Integer tempId =  resultSet.getInt("id");
                if(tempS.contains(tempId)) continue;
                tempS.add(tempId);
                Manufacturer manufacturer =  new Manufacturer(  tempId,
                        resultSet.getNString("name"),
                        resultSet.getNString("note")
                );
                list.add(manufacturer);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
}
