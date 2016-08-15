package sample.model;

import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by Иван on 12.07.2016.
 */


public abstract class ModelDB {

    private static Connection connection;
    protected static Connection getConnection() {
        return connection;
    }
    public static void setConnection(Connection connection) {
        ModelDB.connection = connection;
    }

    //public abstract ObservableList getData();
    //public static  model get(int id){return new model};


    public abstract boolean delete();
    public int delete(String table, int id){
        try {
            //DELETE FROM `troubleshooting`.`forse` WHERE `id`='3';
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "DELETE FROM `troubleshooting`.? " +
                            "WHERE `id`=?;");
            ps.setString(1,table);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    };
    public abstract void update();
    public abstract void insert();




//    protected Map<String, Object> varMap;
//    public abstract void updateFromMap();
//    public Map<String, Object> getVarMap() {
//        return varMap;
//    }
//

}


//
//interface dialogable{
//    Map<String, Object> varMap = null;
//    public void updateFromMap();
//    default public Map<String, Object> getVarMap() {
//        return varMap;
//    }
////    default public boolean updateFromMap(){
////        if(varMap != null){
////            for(Map.Entry<String,Object> entry: varMap.entrySet()){
////
////            }
////            return true;
////        }
////        else{
////            return false;
////        }
////    }
//}
//
//interface Crud<T>{
//    static Connection connection;
//    static Connection getConnection() {
//        return connection;
//    }
//    public static void setConnection(Connection connection) {
//        Crud.connection = connection;
//    }
//
//
//    public ObservableList<T> getData();
//    public void delete();
//    public void update();
//    public void add();
//}

