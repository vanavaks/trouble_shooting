package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

/**
 * Created by Иван on 24.06.2016.
 * нет реализации метода get(int id)
 */
@Table(title = "Под зона", table = "subzone")
public class SubZone extends dialogableModelDB {
    @Column(title = "Название", column = "name")
    @NotNull(message = "Введите название подзоны")
    public String name;
    @Column(title = "Позиция", column = "position")
    @NotNull(message = "Введите позицию")
    public String position;
    @Column(title = "Примечание", column = "note")
    public String note;

    //private static List<Zone> zoneList;

    @Column(title = "Зона", column = "zoneId")
    public Zone zone;
    public SubZone(int id, String name, String position, String note, int zoneId) {
        super.setId(id);// = new SimpleIntegerProperty(id);
        this.name = name ;//new SimpleStringProperty(name);
        this.position = position;
        this.note = note; //new SimpleStringProperty(note);
        this.zone = (Zone)Zone.get(zoneId);

        //For dialog
        //varMap = new HashMap<String, Object>();
//        varMap.put(nameTitle,this.getName());
//        varMap.put(noteTitle, this.getNote());
//        varMap.put(zoneTitle,this.zone);


        //this.zoneId = new SimpleIntegerProperty(zoneId);
        //this.zoneName = new SimpleStringProperty(zoneName);
    }
    public SubZone(String name, String position, String note, int zoneId) {
        this(0,name,position, note,zoneId);
    }
//    public SubZone(String name, String note, int zoneId, ObservableList<Zone> list) {
//        this(name,note,zoneId);
//        zoneList = list;
//    }
    public SubZone(Zone z) {
        this();
        this.zone = z;
    }
    public SubZone() {
        this(0,"","","",0);
    }

    public ObservableList<SubZone> getData(){
        return SubZone.getDataFiltered(null);
//        ObservableList<SubZone> list = FXCollections.observableArrayList();
//        try {
//            Statement statement = ModelDB.getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT  A.id, " +
//                            "A.name, " +
//                            "A.note, " +
//                            "A.zoneId, " +
//                            "B.name " +
//                            "FROM troubleshooting.subzone A " +
//                            "INNER JOIN troubleshooting.zone B " +
//                            "ON(A.zoneId = B.id) "
//            );
//            while(resultSet.next()){
//                list.add(new
//                                SubZone(    resultSet.getInt("id"),
//                                            resultSet.getString("name"),
//                                            resultSet.getString("note"),
//                                            resultSet.getInt("zoneId")
//                        //resultSet.getString("B.name")
//                        )
//                );
//            }
//            return list;
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//        return null;
    }

//    @Override
//    public Gettable<? extends dialogableModelDB> getNew() {
//        return new SubZone();
//    }

    public static ObservableList<SubZone> getDataFiltered(Zone zone){
        ObservableList<SubZone> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM troubleshooting.subzone";
        if(zone != null){
            query += " WHERE `zoneId`=" + zone.getId();
        }
        query += " order by position";
//                "SELECT  A.id, " +
//                "A.name, " +
//                "A.note, " +
//                "A.zoneId, " +
//                "B.name " +
//                "FROM troubleshooting.subzone A " +
//                "INNER JOIN troubleshooting.zone B " +
//                "ON(A.zoneId = B.id) ";

        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                while (resultSet.next()) {
                    list.add(new
                            SubZone(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("position"),
                            resultSet.getString("note"),
                            resultSet.getInt("zoneId")
                            //resultSet.getString("B.name")
                    ));
                }
                return list;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static SubZone  get(int id){
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM troubleshooting.subzone " +
                            "where id = " + id + ";"
//                    "SELECT  A.id, " +
//                            "A.name, " +
//                            "A.note, " +
//                            "A.zoneId, " +
//                            "B.name " +
//                            "FROM troubleshooting.subzone A " +
//                            "INNER JOIN troubleshooting.zone B " +
//                            "ON(A.zoneId = B.id) " +
//                            "where id = " + id + ";"

            );
            if(resultSet.next()){
                return new
                                SubZone(    resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("position"),
                                resultSet.getString("note"),
                                resultSet.getInt("zoneId")
                                //resultSet.getString("B.name")
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
        //Return first or default
    public static SubZone get(){
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT  A.id, " +
                            "A.name, " +
                            "A.position" +
                            "A.note, " +
                            "A.zoneId, " +
                            "B.name " +
                            "FROM troubleshooting.subzone A " +
                            "INNER JOIN troubleshooting.zone B " +
                            "ON(A.zoneId = B.id) " +
                            " order by id asc limit 1 ;"
            );
            if(resultSet.next()){
                return new
                        SubZone(    resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getString("note"),
                        resultSet.getInt("zoneId")
                        //resultSet.getString("B.name")
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<SubZone> get(Zone z){
        ObservableList<SubZone> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM troubleshooting.subzone " +
                            "where zoneId = " + z.getId() + ";"
//                    "SELECT  A.id, " +
//                            "A.name, " +
//                            "A.note, " +
//                            "A.zoneId, " +
//                            "B.name " +
//                            "FROM troubleshooting.subzone A " +
//                            "INNER JOIN troubleshooting.zone B " +
//                            "ON(A.zoneId = B.id) " +
//                            "where id = " + id + ";"

            );
            while(resultSet.next()){
                list.add(new
                        SubZone(    resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("position"),
                        resultSet.getString("note"),
                        resultSet.getInt("zoneId")
                        //resultSet.getString("B.name")
                ));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    public void insert(){
        try{
            Statement statement = ModelDB.getConnection().createStatement();
            statement.execute(
                    "INSERT INTO `troubleshooting`.`subzone`" +
                            " (`name`, `position`, `note`, `zoneId`) " +
                            "VALUES ('" +
                            this.getName() + "', '" +
                            this.getPosition() + "','" +
                            this.getNote() + "', '" +
                            this.zone.getId() + "');"
            );
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
//    public void delete(){
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "DELETE FROM `troubleshooting`.`subzone` " +
//                            "WHERE `id`= ?;"
//            );
//            ps.setInt(1, this.getId());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }

    public boolean delete(){
        ObservableList<Equipment> list = Equipment.getDataFiltered(this,null);
        if(list.size() == 0){
            return super.delete();
        }
        return false;
    }

    public static SubZone createNew() {
        return new SubZone();
    }

    public void update(){
        try{
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                     "UPDATE `troubleshooting`.`subzone` " +
                            "SET " +
                            "`name` = ?, " +
                             "`position` = ?, " +
                            "`note` = ?, " +
                            "`zoneId` = ? " +
                            "WHERE `id` = ?;"
            );
            ps.setString(1, this.getName());
            ps.setString(2, this.getPosition());
            ps.setString(3, this.getNote());
            ps.setInt(4, this.zone.getId());
            ps.setInt(5, this.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    public Zone getZone(){
        return zone;
    }
    public void setZone(Zone zone) {
        this.zone = zone;
    }
//    public int getZoneId() {
//        return zoneId.get();
//    }
//    public IntegerProperty zoneIdProperty() {
//        return zoneId;
//    }
//    public void setZoneId(int zoneId) {
//        this.zoneId.set(zoneId);
//    }
//    public String getZoneName() {
//        return zoneName.get();
//    }
//    public StringProperty zoneNameProperty() {
//        return zoneName;
//    }
//    public void setZoneName(String zoneName) {
//        this.zoneName.set(zoneName);
//    }

    @Override
    public String toString() {
        return this.getPosition() + " " + this.getName();
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
}
