package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.*;

import java.sql.*;

/**
 * Created by Иван on 11.04.2016.
 */
@Table(title = "PLC", table = "plc")
public class PLC extends dialogableModelDB {
    @Column(title = "Название", column = "label")
    @NotNull(message = "Введите название")
    public String name;
    @Column(title = "Пароль", column = "password")
    public String password;

    public PLC(int id, String name, String password) {
        super.setId(id);
        this.name = name;
        this.password = password;
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
                            "FROM troubleshooting.PLC " +
                            " order by label "
            );
            while(resultSet.next()){
                if(resultSet.getInt("id") == 0) continue;
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
    @Override
    public boolean delete()throws SQLException{
        ObservableList<Force> list = Force.getDataFiltered(0,0,this.getId(),null,null);
        if(list.size() == 0){
            return super.delete();
        }
        return false;
    }

//    public void insert(){
//        try{
////            PreparedStatement ps = connection.prepareStatement(
////                    "INSERT INTO troubleshooting.plc (" +
////                            "id, " +
////                            "label, " +
////                            "password" +
////                    ") VALUES (?, ?, ?,);"
////            );
////            ps.setInt(1, plc.getId());
////            System.out.println(plc.getName());
////            ps.setString(2, plc.getName());
////            System.out.println(plc.getPassword());
////            ps.setString(3, plc.getPassword());
////            ps.executeUpdate();
//
//            Statement statement = ModelDB.getConnection().createStatement();
//            statement.execute(
//                    "INSERT INTO `troubleshooting`.`plc`" +
//                            " (`id`, `label`, `password`) " +
//                    "VALUES ('" +
//                            this.getId() + "', '" +
//                            this.getName() + "', '" +
//                            this.getPassword() + "');"
//
//            );
//            //INSERT INTO `troubleshooting`.`iniciator` (`inicId`, `inicName`, `inicNote`) VALUES ('3', 'Шевело', 'генеральный директор');
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//    public void update(){
//        try{
//            // its worked query
////            Statement statement = connection.createStatement();
////            String s =  "UPDATE `troubleshooting`.`plc` " +
////                        "SET " +
////                            "`label` = '" + plc.getName() +
////                            "', `password` = '" + plc.getPassword() +
////                        "' WHERE `id` = '" +  plc.getId() + "';";
////            statement.executeUpdate(s);
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "UPDATE `troubleshooting`.`plc` " +
//                    "SET " +
//                            "`label` = ?, " +
//                            "`password` = ? " +
//                    "WHERE `id` = ?;"
//            );
//
//            ps.setString(1, this.getName());
//            ps.setString(2, this.getPassword());
//            ps.setInt(3, this.getId());
//            ps.executeUpdate();
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//    public void delete(){
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "DELETE FROM `troubleshooting`.`plc`" +
//                    "WHERE `id`= ?;"
//                    );
//            ps.setInt(1, this.getId());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
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


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override public String toString(){
        return name;
    }
}
