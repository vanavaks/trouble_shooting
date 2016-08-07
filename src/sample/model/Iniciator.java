package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by Иван on 03.05.2016.
 */
public class Iniciator extends dialogableModelDB {
    private int id;
    private static final String nameTitle = "Имя";
    private String name;
    private static final String noteTitle = "Особые Приметы";
    private String note;

    public Iniciator(int id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;

        super.varMap.put(nameTitle, name);
        super.varMap.put(noteTitle, note);
    }
    public Iniciator(){}

    public ObservableList<Iniciator> getData(){
        ObservableList<Iniciator> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.iniciator "
            );
            while(resultSet.next()){
                list.add(new
                                Iniciator(  resultSet.getInt("inicId"),
                                            resultSet.getString("inicName"),
                                            resultSet.getString("inicNote")
                                          )
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static Iniciator get(int id){
        Iniciator iniciator = null;
        try{
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.iniciator " +
                            "where inicId = " + id + " ;"
            );
            if(resultSet.next()){
                iniciator = new Iniciator(
                        resultSet.getInt("inicId"),
                        resultSet.getString("inicName"),
                        resultSet.getString("inicNote")
                );
                //System.out.println(plc.getId() + plc.getName() + plc.getPassword());
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return iniciator;

    }
    public void delete(){
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "DELETE FROM `troubleshooting`.`iniciator` " +
                            "WHERE `inicId`= ?;"
            );
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void update(){
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    //UPDATE `troubleshooting`.`iniciator` SET `inicId`='4', `inicName`='Шевело1', `inicNote`='генеральный директор1' WHERE `inicId`='3';

            "UPDATE `troubleshooting`.`iniciator` " +
                            "SET " +
                            //"`inicId` = ?, " +
                            "`inicName` = ?, " +
                            "`inicNote` = ? " +
                            "WHERE `inicId` = ?;"
            );
            ps.setString(1, this.getName());
            ps.setString(2, this.getNote());
            ps.setInt(3, this.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void insert(){
        try{
            Statement statement = ModelDB.getConnection().createStatement();
            statement.execute(
                    //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('4', 'bjk', 'fcfcjhg');
                    "INSERT INTO `troubleshooting`.`iniciator`" +
                            " (`inicId`, `inicName`, `inicNote`) " +
                            "VALUES ('" +
                            this.getId() + "', '" +
                            this.getName() + "', '" +
                            this.getNote() + "');"

            );
            //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('3', 'Шевело', 'генеральный директор');
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    @Override public String toString(){
        return this.getName();
    }
    public void updateFromMap() {
        this.setName((String) varMap.get(nameTitle));
        this.setNote((String) varMap.get(noteTitle));
    }
}
