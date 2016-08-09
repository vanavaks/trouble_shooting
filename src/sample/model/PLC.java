package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by Иван on 11.04.2016.
 */
public class PLC extends dialogableModelDB {
    private IntegerProperty id;
    private StringProperty name;
    private final static String nameTitle = "Название";
    private StringProperty password;
    private final static String passTitle = "Пароль";

    public PLC(int id, String name, String password) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);

//        varMap = new HashMap<String, Object>();
//        super.varMap.put(nameTitle,name);
//        super.varMap.put(passTitle,password);
    }
    public PLC() {
        this(0,"","");
    }

    public static PLC get(int id){
        PLC plc = null;
        try {
            Statement statement =  ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.PLC " +
                            "where id = " + id + " ;"
            );
            if(resultSet.next()){
                plc = new PLC(
                        resultSet.getInt("id"),
                        resultSet.getString("label"),
                        resultSet.getString("password")
                );
                System.out.println(plc.getId() + plc.getName() + plc.getPassword());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return plc;
    }
    public  ObservableList<PLC> getData(){
        ObservableList<PLC> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.PLC "
            );
            while(resultSet.next()){
                list.add(new
                        PLC(   resultSet.getInt("id"),
                                    resultSet.getString("label"),
                                    resultSet.getString("password")
                            )
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public void insert(){
        try{
//            PreparedStatement ps = connection.prepareStatement(
//                    "INSERT INTO troubleshooting.plc (" +
//                            "id, " +
//                            "label, " +
//                            "password" +
//                    ") VALUES (?, ?, ?,);"
//            );
//            ps.setInt(1, plc.getId());
//            System.out.println(plc.getName());
//            ps.setString(2, plc.getName());
//            System.out.println(plc.getPassword());
//            ps.setString(3, plc.getPassword());
//            ps.executeUpdate();

            Statement statement = ModelDB.getConnection().createStatement();
            statement.execute(
                    "INSERT INTO `troubleshooting`.`plc`" +
                            " (`id`, `label`, `password`) " +
                    "VALUES ('" +
                            this.getId() + "', '" +
                            this.getName() + "', '" +
                            this.getPassword() + "');"

            );
            //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('3', 'Шевело', 'генеральный директор');
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(){
        try{
            // its worked query
//            Statement statement = connection.createStatement();
//            String s =  "UPDATE `troubleshooting`.`plc` " +
//                        "SET " +
//                            "`label` = '" + plc.getName() +
//                            "', `password` = '" + plc.getPassword() +
//                        "' WHERE `id` = '" +  plc.getId() + "';";
//            statement.executeUpdate(s);
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "UPDATE `troubleshooting`.`plc` " +
                    "SET " +
                            "`label` = ?, " +
                            "`password` = ? " +
                    "WHERE `id` = ?;"
            );

            ps.setString(1, this.getName());
            ps.setString(2, this.getPassword());
            ps.setInt(3, this.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(){
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "DELETE FROM `troubleshooting`.`plc`" +
                    "WHERE `id`= ?;"
                    );
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//    public static ObservableList<String> getPLCNames(){
//        ObservableList<String> list = FXCollections.observableArrayList();
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT label " +
//                            "FROM troubleshooting.PLC "
//            );
//            while(resultSet.next()){
//                list.add(new String(resultSet.getString("label")));
//            }
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//        return list;
//    }

    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public String getPassword() {
        return password.get();
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public void setPassword(String password) {
        this.password.set(password);
    }

    @Override public String toString(){
        return name.get();
    }
//      public void updateFromMap() {
//        this.setName((String)this.varMap.get(nameTitle));
//        this.setPassword((String)this.varMap.get(passTitle));
//    }
}
