package sample.model;

import javafx.collections.ObservableList;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Иван on 12.07.2016.
 */
//@Retention(RetentionPolicy.RUNTIME)
//@interface Column{
//    String title();
//    String column();
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@interface EditedFields {
//    String[] value() default {};
//}
//
//@Retention(RetentionPolicy.RUNTIME)
//@interface Table {
//    String title();
//    String table();
//}

public abstract class dialogableModelDB extends ModelDB implements Cloneable {
//    protected Map<String, Object> varMap;
//        //Function update model from map
//    public dialogableModelDB(){varMap = new HashMap<String, Object>();}

    @Override
    public Object clone(){
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Ошибка клонирования dialogable model");
            e.printStackTrace();
        }
        return null;
    }

//    //public abstract void updateFromMap();
//    public Map<String, Object> getVarMap() {
//        return varMap;
//    }

    private String addVal(String value, String column){
        return value + column + ", ";
    }
    public abstract ObservableList<?> getData();
    //public abstract Gettable<? extends dialogableModelDB> getNew();

    public Field[] getFields(){
        Class<?> c = this.getClass();
        return c.getFields();
    }

    //db
//    public ObservableList<dialogableModelDB> getData(model M){
//        ObservableList<model> list = FXCollections.observableArrayList();
//        Class<?> c = this.getClass();
//        if(c.getFields() != null){
//            //set a name of table
//            Table table = c.getAnnotation(Table.class);
//            String  query = "SELECT ";
//            for (Field f: c.getFields()) {
//                Column column = f.getAnnotation(Column.class);
//                //add new column to query
//                query += column.column() + ", ";
//            }
//            query += " FROM troubleshooting." + table.table() + " ;";
//
//            try{
//                Statement statement = ModelDB.getConnection().createStatement();
//                ResultSet rs = statement.executeQuery(query);
//
//                while(rs.next()){
//
//                    list.add(new  Force(
//                                    rs.getInt("id"),
//                                    rs.getInt("engineer_id"),
//                                    //rs.getString("firstName"),
//                                    //rs.getString("lastName"),
//                                    rs.getInt("initiator_id"), //Here must be in
//                                    //rs.getString("inicName"),
//                                    rs.getDate("date"),
//                                    rs.getInt("PLC_id"),
//                                    //rs.getString("label"),
//                                    //rs.getString("name"),
//                                    rs.getString("adress"),
//                                    rs.getString("note")
//                            )
//                    );
//
//
//                }
//            }
//            catch (SQLException e){
//                e.printStackTrace();
//            }}
//        return list;
//        return null;
//    }


//    public dialogableModelDB get(int id){
//        return null;
//    }

    public void validate(){
        Class<?> c = this.getClass();
        if(c.getFields() != null) {
            try {
                for (Field f: c.getFields()) {
                    //validate(f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validate(String s){

        return false;
    }


    @Override
    public void insert(){
        //List<Field> fieldList = getEditedFields(model);
        Class<?> c = this.getClass();
        if(c.getFields() != null){
        try {
                    //set a name of table
            Table table = c.getAnnotation(Table.class);
            //ps.setString(1,table.table());
            String  insert = "INSERT INTO `troubleshooting`.`" + table.table() + "` (";
            String  values = ") VALUES (";
            boolean notFirst = false;
            for (Field f: c.getFields()) {
                if (notFirst) {insert += ", "; values += ", ";}
                Column column = f.getAnnotation(Column.class);
                        //add new column to query  //addVal(insert, col);
                String col = column.column();
                insert += "`" + col + "`";
                        //add new value to query   //addVal(values, f.get(model).toString());
                try {
                    if(f.get(this) instanceof dialogableModelDB){       //save id of model to DB
                        values += "'" + ((dialogableModelDB)f.get(this)).getId() + "'";
                    }
                    else if (f.get(this) instanceof Boolean){       //save id of model to DB
                        values += ((Boolean)f.get(this)).toString();
                    }
                    else if (f.get(this) instanceof java.util.Date){       //save id of model to DB
                        int year =  ((java.util.Date)f.get(this)).getYear() + 1900;
                        int month = ((java.util.Date)f.get(this)).getMonth() + 1;
                        int day = ((java.util.Date)f.get(this)).getDay();
                        values += "'" + year + "-" + month + "-" + day + "'" ;
                    }
                    else {
                        values += "'" + f.get(this).toString() + "'";
                    }
                } catch (IllegalAccessException e) {e.printStackTrace(); return;}

                notFirst = true;
//                if(f.getType().getTypeName() == "String"){
//                    try {
//                        String s = (String)f.get(model);
//                        addVal(values, s);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                    System.out.println(f);
//                }
//                if(f.getType().getTypeName() == "Integer"){
//                    System.out.println(f);
//                }
            }
            String query = insert + values + ");";
            System.out.println(query);

            PreparedStatement ps = ModelDB.getConnection().prepareStatement(query);
            ps.executeUpdate();

//            for (Map.Entry<String,Object> e: this.getVarMap().entrySet()) {
//                query += ", ?";
//            }
//
//
//
//            int n = 1;
//            for (Map.Entry<String,Object> e: this.getVarMap().entrySet()) {
//
//                if (e.getValue() instanceof String){
//                    ps.setString(n,(String)e.getValue());
//                }
//                else if(e.getValue() instanceof Integer){
//                    ps.setInt(n,(Integer)e.getValue());
//                }
//                else if(e.getValue() instanceof java.util.Date){
//                    ps.setDate(n, (java.util.Date)(e.getValue()).getSQLDate();
//                }
//                n++;
//            }
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "INSERT INTO `troubleshooting`.`forse` (`id`, `engineer_id`, `date`, `initiator_id`, `PLC_id`, `adress`, `note`) " +
//                            "VALUES (0, ?, ?, ?, ?, ?, ?);");
//            ps.setInt(1,this.engineer.getId());
//            ps.setDate(2,this.getSQLDate());
//            ps.setInt(3,this.iniciator.getId());
//            ps.setInt(4, this.plc.getId());
//            ps.setString(5,this.getAdress());
//            ps.setString(6, this.getNote());
//            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
        //return 0;
    }
    @Override
    public void update(){
        Class<?> c = this.getClass();
        if(c.getFields() != null){
            try {
                //set a name of table
                Table table = c.getAnnotation(Table.class);
                String  query = "UPDATE `troubleshooting`.`" + table.table() + "` SET " ;
                boolean notFirst = false;
                Field[] lst = this.getFields();
                for (Field f: lst) {
                    //System.out.println(f);
                    if (notFirst) {query += ", ";}    //tyt "', `"
                    try {
                        Column column = f.getAnnotation(Column.class);
                        //add new column to query
                        query += "`" + column.column() + "`=";  //tyt "`='"
                        //add new value to query
                        if(f.get(this) instanceof dialogableModelDB){       //save id of model to DB
                            query += "'" + ((dialogableModelDB)f.get(this)).getId() + "'";
                        }
                        else if (f.get(this) instanceof Boolean){       //save id of model to DB
                            query += ((Boolean)f.get(this)).toString();
                        }
                        else if (f.get(this) instanceof java.util.Date){       //save id of model to DB
                            int year =  ((java.util.Date)f.get(this)).getYear() + 1900;
                            int month = ((java.util.Date)f.get(this)).getMonth() + 1;
                            int day = ((java.util.Date)f.get(this)).getDay();
                            query += "'" + year + "-" + month + "-" + day + "'" ;
                        }
                        else {
                            query += "'" + f.get(this).toString() + "'";
                        }
                    } catch (IllegalAccessException e) {e.printStackTrace(); return;}
                    notFirst = true;
                }
                query += " WHERE `id`= ?;"; // "' WHERE `id`= ?;"
                System.out.println(query);

                PreparedStatement ps = ModelDB.getConnection().prepareStatement(query);
                ps.setInt(1, this.getId());
                ps.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
        //return 0;
    }
    @Override
    public void delete(){
        Class<?> c = this.getClass();
        if(c.getFields() != null) {
            try {
                Table table = c.getAnnotation(Table.class);
                //DELETE FROM `troubleshooting`.`forse` WHERE `id`='3';
                PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                        "DELETE FROM `troubleshooting`.`" + table.table() +
                                "` WHERE `id`=?;");
                //ps.setString(1, table.table());
                ps.setInt(1, this.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static List<Field> getEditedFields(dialogableModelDB o) {
        List<Field> fieldList = null;
        Class<?> c = o.getClass();
        Field[] F = c.getFields();
        for (Field f : F) {
            if (f.getAnnotation(Column.class) != null) {
                fieldList.add(f);
            }
        }
        return fieldList;
    }

//        try {

//            EditedFields F = c.getAnnotation(EditedFields.class);
//            List<Field> fieldList = null;
//            for (String s : F.value()) {
//                Field m = c.getField(s);
//                fieldList.add(c.getField(s));
//            }
//        }
//        catch(NoSuchFieldException e){
//            e.printStackTrace();//       }


//    public List<Field> getFields(){
//        Class<?> c = o.getClass();
//        try {
//            EditedFields F = c.getAnnotation(EditedFields.class);
//            List<Field> fieldList = null;
//            for (String s : F.value()) {
//                Field m = c.getField(s);
//
//                if () {
//                    fieldList.add(m);
//                }
//            }
//        }
//        catch(NoSuchFieldException e){
//            e.printStackTrace();//       }
//    }

//    default public boolean updateFromMap(){
//        if(varMap != null){
//            for(Map.Entry<String,Object> entry: varMap.entrySet()){
//
//            }
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
}


