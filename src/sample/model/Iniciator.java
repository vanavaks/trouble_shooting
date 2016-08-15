package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Created by Иван on 03.05.2016.
 */
@Table(title = "Инициатор", table = "iniciator")
public class Iniciator extends dialogableModelDB {
    @Column(title = "Имя", column = "inicName")
    @NotNull(message = "Введите имя и фамилию")
    public String name;
    @Column(title = "Примечание", column = "inicNote")
    public String note;

    public Iniciator(int id, String name, String note) {
        super.setId(id);
        this.name = name;
        this.note = note;
    }
    public Iniciator(){this(0,"","");}

    public ObservableList<Iniciator> getData(){
        ObservableList<Iniciator> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.iniciator " +
                            " order by inicName "
            );
            while(resultSet.next()){
                if(resultSet.getInt("id") == 0) continue;
                list.add(new
                                Iniciator(  resultSet.getInt("id"),
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
    public static Iniciator get(int id) {
        Iniciator iniciator = null;
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.iniciator " +
                            "where id = " + id + " ;"
            );
            if (resultSet.next()) {
                iniciator = new Iniciator(
                        resultSet.getInt("id"),
                        resultSet.getString("inicName"),
                        resultSet.getString("inicNote")
                );
                //System.out.println(plc.getId() + plc.getName() + plc.getPassword());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return iniciator;
    }
    @Override
    public boolean delete(){
        ObservableList<Force> list = Force.getDataFiltered(0,this.getId(),0,null,null);
        if(list.size() == 0){
            super.delete();
            return true;
        }
        return false;
    }
//    }
//    public void delete(){
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "DELETE FROM `troubleshooting`.`iniciator` " +
//                            "WHERE `inicId`= ?;"
//            );
//            ps.setInt(1, this.getId());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void update(){
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    //UPDATE `troubleshooting`.`iniciator` SET `inicId`='4', `inicName`='Шевело1', `inicNote`='генеральный директор1' WHERE `inicId`='3';
//
//            "UPDATE `troubleshooting`.`iniciator` " +
//                            "SET " +
//                            //"`inicId` = ?, " +
//                            "`inicName` = ?, " +
//                            "`inicNote` = ? " +
//                            "WHERE `inicId` = ?;"
//            );
//            ps.setString(1, this.getName());
//            ps.setString(2, this.getNote());
//            ps.setInt(3, this.getId());
//            ps.executeUpdate();
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//    public void insert(){
//        try{
//            Statement statement = ModelDB.getConnection().createStatement();
//            statement.execute(
//                    //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('4', 'bjk', 'fcfcjhg');
//                    "INSERT INTO `troubleshooting`.`iniciator`" +
//                            " (`inicId`, `inicName`, `inicNote`) " +
//                            "VALUES ('" +
//                            this.getId() + "', '" +
//                            this.getName() + "', '" +
//                            this.getNote() + "');"
//
//            );
//            //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('3', 'Шевело', 'генеральный директор');
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }

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
//    }
}
