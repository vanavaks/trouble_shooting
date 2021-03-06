package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Mothers.Column;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.Table;
import sample.model.Mothers.dialogableModelDB;

import java.sql.*;

/**
 * Created by Иван on 11.04.2016.
 */
@Table(title = "Зона", table = "Zone")
public class Zone extends dialogableModelDB {
    @Column(title = "Позиция", column = "position")
    @NotNull(message = "Введите позицию")
    public String position;
    @Column(title = "Название", column = "name")
    @NotNull(message = "Введите название зоны")
    public String name;
    @Column(title = "Примечание", column = "note")
    public String note;

    public Zone(int id, String position, String name, String note) {
        super.setId(id);
        this.position = position;
        this.name = name;
        this.note = note;
    }
    public  Zone(){
        this(0,"","","");
    }
    //public SubZone getZone() {return new SubZone();}

    public static Zone  get(int id){
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.zone " +
                            "where id = " + id + ";"
            );
            if (resultSet.next()) {
                return
                    new Zone(   resultSet.getInt("id"),
                                resultSet.getString("position"),
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
    public ObservableList<Zone> getData(){

        ObservableList<Zone> list = FXCollections.observableArrayList();
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                    "FROM troubleshooting.zone " +
                    " order by position "
            );
            while (resultSet.next()) {
                list.add(
                        new Zone(   resultSet.getInt("id"),
                                    resultSet.getString("position"),
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
//    public void insert(){
//        try {
//            PreparedStatement ps = getConnection().prepareStatement(
//                    "INSERT INTO `troubleshooting`.`zone` (`id`, `position`, `name`, `note`) " +
//                    "VALUES (0, ?, ?, ?);"
//            );
//            ps.setString(1, this.getPosition());
//            ps.setString(2, this.getName());
//            ps.setString(3, this.getNote());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean delete()throws SQLException{
        if(SubZone.get(this) != null){
            ObservableList<SubZone> list =  SubZone.getDataFiltered(this);
            if(list.size() > 0) return false;
            //if(sz.size() > 0) return;
            return super.delete();
//            try{
//                PreparedStatement ps = getConnection().prepareStatement(
//                        "DELETE FROM `troubleshooting`.`zone`" +
//                                "WHERE `id`= ?;"
//                );
//                ps.setInt(1, this.getId());
//                ps.executeUpdate();
//            }
//            catch(Exception e){
//                System.out.println("Can't delete this zone" + this);
//                e.printStackTrace();
//            }
        }
        return false;
    }
//    public void update(){
//        try {
//            //UPDATE `troubleshooting`.`zone` SET `id`='4', `position`='4300', `name`='Формлиниx', `note`='asf' WHERE `id`='3';
//            PreparedStatement ps = getConnection().prepareStatement(
//                    "UPDATE `troubleshooting`.`zone` " +
//                            "SET " +
//                            "`position` = ?, " +
//                            "`name` = ?, " +
//                            "`note` = ? " +
//                            "WHERE `id` = ?;"
//            );
//
//            ps.setString(1, this.getPosition());
//            ps.setString(2, this.getName());
//            ps.setString(3, this.getNote());
//            ps.setInt(4, this.getId());
//            System.out.println(getId() + getPosition() + getName() + this.getNote());
//            ps.executeUpdate();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    @Override public String toString(){
        return position + " " + name;
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
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public Zone createNew() {
        return new Zone();
    }
}
