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
 * Created by Иван on 08.04.2016.
 */
public class Engineer extends dialogableModelDB {
    private IntegerProperty id;
    private StringProperty firstName;
    private static final String firstNameTitle = "Имя";
    private StringProperty lastName;
    private static final String lastNameTitle = "Фамилия";
    private StringProperty shift;
    private static final String shiftTitle = "Смена";

    public Engineer(){}
    public Engineer(int id, String firstName, String lastName, String shift){
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.shift = new SimpleStringProperty(shift);

        varMap = new HashMap<String, Object>();
        super.varMap.put(firstNameTitle,this.getFirstName());
        super.varMap.put(lastNameTitle,this.getLastName());
        super.varMap.put(shiftTitle,this.getShift());
    }

    public ObservableList<Engineer> getData(){
        ObservableList<Engineer> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.engineer " /*+
                            "where id = 1;"*/
            );
            while (resultSet.next()) {
                list.add(
                        new Engineer(   resultSet.getInt("id"),
                                        resultSet.getString("firstName"),
                                        resultSet.getString("lastName"),
                                        resultSet.getNString("shift")
                        )

                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static Engineer get(int id){
        Engineer eng = null;
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.engineer " +
                            "where id = " + id + ";"
            );
            if (resultSet.next()) {
                eng = new Engineer(   resultSet.getInt("id"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getNString("shift")
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return eng;
    }
    public void insert(){
        //INSERT INTO `troubleshooting`.`engineer` (`id`, `firstName`, `lastName`, `shift`) VALUES ('4', 'Leniv', 'Vitaliy', 'C');
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "INSERT INTO `troubleshooting`.`engineer` (`id`, `firstName`, `lastName`, `shift`) " +
                    "VALUES (?, ?, ?, ?);"
            );
            ps.setInt(1, 0);
            ps.setString(2, this.getFirstName());
            ps.setString(3, this.getLastName());
            ps.setString(4, this.getShift());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void delete(){
        //DELETE FROM `troubleshooting`.`engineer` WHERE `id`='3';
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "DELETE FROM `troubleshooting`.`engineer` " +
                    "WHERE `id`=?;"
            );
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void update(){
        //UPDATE `troubleshooting`.`engineer` SET `firstName`='Ruslan1', `lastName`='Galickyi2', `shift`='vf' WHERE `id`='2';
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "UPDATE `troubleshooting`.`engineer` " +
                    "SET `firstName`=?, " +
                    "`lastName`=?, " +
                    "`shift`=? " +
                    "WHERE `id`=?;"
            );
            ps.setString(1, this.getFirstName());
            ps.setString(2, this.getLastName());
            ps.setString(3, this.getShift());
            ps.setInt(4, this.getId());
            ps.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getFullName(){
        return this.getFirstName() + this.getLastName();
    }
    public int getId() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getFirstName() {
        return firstName.get();
    }
    public StringProperty firstNameProperty() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public String getLastName() {
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public String getShift() {
        return shift.get();
    }
    public StringProperty shiftProperty() {
        return shift;
    }
    public void setShift(String shift) {
        this.shift.set(shift);
    }

    @Override public String toString(){
        return firstName.get() + " " + lastName.get();
    }
    public void updateFromMap() {
        this.setFirstName((String) (this.varMap).get(firstNameTitle));
        this.setLastName((String) (this.varMap).get(lastNameTitle));
        this.setShift((String) (this.varMap).get(shiftTitle));
    }

}
