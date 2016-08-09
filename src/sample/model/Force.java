package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Date;

/**
 * Created by Иван on 11.04.2016.
 */
public class Force extends dialogableModelDB {
//    private IntegerProperty engineer_id;
//    private StringProperty engineerName;
//    private IntegerProperty iniciator_id;
//    private StringProperty initiator_name;
//    private IntegerProperty PLC_id;
//    private StringProperty PLC;

    private IntegerProperty id;
    private static final String dateTitle = "Дата";
    private java.util.Date date;
    private static final String adressTitle = "Адрес";
    private StringProperty adress;
    private static final String noteTitle = "Описание";
    private StringProperty note;
    private static final String engineerTitle = "Инженер";
    private Engineer engineer;
    private static final String plcTitle = "PLC";
    private PLC plc;
    private static final String iniciatorTitle = "Инициатор";
    private Iniciator iniciator;
    private Trouble trouble;

    ObservableList<Engineer> engList;
    ObservableList<PLC> plcList;
    ObservableList<Iniciator> iniciatorList;
    public Force(){}
    public Force(int id, int engineer_id, int iniciator_id, Date date, int PLC_id, String adress, String note) {
        this.id = new SimpleIntegerProperty(id);
        this.engineer = Engineer.get(engineer_id);
        this.date = new java.util.Date(date.getTime());
        this.iniciator = Iniciator.get(iniciator_id);
        this.plc = PLC.get(PLC_id);
        this.adress = new SimpleStringProperty(adress);
        this.note = new SimpleStringProperty(note);
        //this.PLC_id = new SimpleIntegerProperty(PLC_id);
        //this.PLC = new SimpleStringProperty(PLC);
        //this._date = LocalDate(date);
        //this.date = convertFromSQLDateToJAVADate(date);
        //this.lastName = new SimpleStringProperty(lastName);
        //this.initiator_name = new SimpleStringProperty(initiator_name);

        //this.engineerName = new SimpleStringProperty(firstName + lastName);

//        varMap.put(dateTitle,this.getDate());
//        varMap.put(noteTitle, this.getNote());
//        varMap.put(adressTitle,this.getAdress());
//        varMap.put(engineerTitle,this.engineer);
//        varMap.put(plcTitle, this.plc);
//        varMap.put(iniciatorTitle,this.iniciator);
    }
    public Force(int id, int engineer_id, int iniciator_id, Date date, int PLC_id, String adress, String note, ObservableList<Engineer> engList, ObservableList<PLC> plcList, ObservableList<Iniciator> inicList){
        this(id,engineer_id,iniciator_id,date,PLC_id,adress,note);
        this.engList = engList;
        this.plcList = plcList;
        this.iniciatorList = inicList;
    }
//    public Force(int id, int engineer_id, int iniciator_id, java.util.Date date, int PLC_id, String adress, String note){
//        this.id = new SimpleIntegerProperty(id);
//        this.engineer_id = new SimpleIntegerProperty(engineer_id);
//        this.date = new java.util.Date(date.getTime());
//        this.iniciator_id = new SimpleIntegerProperty(iniciator_id);
//        this.PLC_id = new SimpleIntegerProperty(PLC_id);
//        this.adress = new SimpleStringProperty(adress);
//        this.note = new SimpleStringProperty(note);
//    }

    public static Force get(int id){
        ObservableList<Force> list = FXCollections.observableArrayList();

        String query =  "SELECT  A.id," +
                "A.engineer_id," +
                "A.initiator_id," +
                "A.date," +
                "A.PLC_id," +
                "A.adress," +
                "A.note," +
                "B.firstName," +
                "B.lastName," +
                "C.label," +
                "D.inicName " +
                "FROM troubleshooting.forse A " +
                "INNER JOIN troubleshooting.engineer B " +
                "ON(A.engineer_id = B.id) " +
                "INNER JOIN troubleshooting.plc C " +
                "ON(A.PLC_id = C.id) " +
                "INNER JOIN troubleshooting.iniciator D " +
                "ON A.initiator_id = D.inicId ";
        if (true){
            try{
                Statement statement = ModelDB.getConnection().createStatement();
                ResultSet rs = statement.executeQuery(query);

                while(rs.next()){
                    list.add(new  Force(
                                    rs.getInt("id"),
                                    rs.getInt("engineer_id"),
                                    //rs.getString("firstName"),
                                    //rs.getString("lastName"),
                                    rs.getInt("initiator_id"), //Here must be in
                                    //rs.getString("inicName"),
                                    rs.getDate("date"),
                                    rs.getInt("PLC_id"),
                                    //rs.getString("label"),
                                    //rs.getString("name"),
                                    rs.getString("adress"),
                                    rs.getString("note")
                            )
                    );


                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }}
        return  null;
    }
    public static ObservableList<Force> getDataFiltered(int engineer_id, int initiator_id, int PLC_id, Date startData, Date endDate){
        ObservableList<Force> list = FXCollections.observableArrayList();

        String query =  "SELECT  A.id," +
                        "A.engineer_id," +
                        "A.initiator_id," +
                        "A.date," +
                        "A.PLC_id," +
                        "A.adress," +
                        "A.note," +
                        "B.firstName," +
                        "B.lastName," +
                        "C.label," +
                        "D.inicName " +
                        "FROM troubleshooting.forse A " +
                        "INNER JOIN troubleshooting.engineer B " +
                        "ON(A.engineer_id = B.id) " +
                        "INNER JOIN troubleshooting.plc C " +
                        "ON(A.PLC_id = C.id) " +
                        "INNER JOIN troubleshooting.iniciator D " +
                        "ON A.initiator_id = D.inicId ";



        if(engineer_id >0){
            query += " WHERE `A`.`engineer_id`=" + engineer_id;
            if(initiator_id >0) {
                query += " AND `A`.`initiator_id`=" + initiator_id;
            }
            if(PLC_id > 0){
                query += " AND `A`.`PLC_id`=" + PLC_id ;
            }
        }
        else if(initiator_id >0){
            query += " WHERE `A`.`initiator_id`=" + initiator_id;
            if(PLC_id > 0){
                query += " AND `A`.`PLC_id`=" + PLC_id ;
            }
        }
        else if(PLC_id > 0){
            query += " WHERE `A`.`PLC_id`=" + PLC_id ;
        }




        if (true){
            try{
                Statement statement = ModelDB.getConnection().createStatement();
                ResultSet rs = statement.executeQuery(query);

                while(rs.next()){
                    list.add(new  Force(
                                    rs.getInt("id"),
                                    rs.getInt("engineer_id"),
                                    //rs.getString("firstName"),
                                    //rs.getString("lastName"),
                                    rs.getInt("initiator_id"), //Here must be in
                                    //rs.getString("inicName"),
                                    rs.getDate("date"),
                                    rs.getInt("PLC_id"),
                                    //rs.getString("label"),
                                    //rs.getString("name"),
                                    rs.getString("adress"),
                                    rs.getString("note")
                            )
                    );


                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }}
        return list;
    }
    public ObservableList<Force> getData(){
        return getDataFiltered(0,0,0,null,null);
    }
    public void delete(){
        try {
            //DELETE FROM `troubleshooting`.`forse` WHERE `id`='3';
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "DELETE FROM `troubleshooting`.`forse` " +
                    "WHERE `id`=?;");
            ps.setInt(1, this.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void update(){
        try {
            //UPDATE `troubleshooting`.`forse` SET `id`='4', `engineer_id`='2', `date`='2015-04-07 00:00:00', `initiator_id`='2',
            //`PLC_id`='1', `adress`='m302.1sfg', `note`='dhasjdu' WHERE `id`='3';
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "UPDATE `troubleshooting`.`forse` " +
                    "SET `engineer_id`=?, " +
                        "`date`=?, " +
                        "`initiator_id`=?, " +
                        "`PLC_id`=?, " +
                        "`adress`=?, " +
                        "`note`=? " +
                    "WHERE `id`= ?;");
            ps.setInt(1, this.engineer.getId());
            ps.setDate(2,this.getSQLDate());
            ps.setInt(3, this.iniciator.getId());
            ps.setInt(4, this.plc.getId());
            ps.setString(5, this.getAdress());
            ps.setString(6, this.getNote());
            ps.setInt(7, this.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void insert(){
        try {
            //INSERT INTO `troubleshooting`.`forse` (`id`, `engineer_id`, `date`, `initiator_id`, `PLC_id`, `adress`, `note`)
            // VALUES ('3', '1', '2015-4-05', '1', '3', 'm302.1', 'none');
            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
                    "INSERT INTO `troubleshooting`.`forse` (`id`, `engineer_id`, `date`, `initiator_id`, `PLC_id`, `adress`, `note`) " +
                    "VALUES (0, ?, ?, ?, ?, ?, ?);");
            ps.setInt(1,this.engineer.getId());
            ps.setDate(2,this.getSQLDate());
            ps.setInt(3,this.iniciator.getId());
            ps.setInt(4, this.plc.getId());
            ps.setString(5,this.getAdress());
            ps.setString(6, this.getNote());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static java.util.Date convertFromSQLDateToJAVADate(java.sql.Date sqlDate) {
        java.util.Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new Date(sqlDate.getTime());
        }
        return sqlDate;
    }
    private java.sql.Date getSQLDate(){
        java.sql.Date date = null;
        if(this.getDate() != null){
            System.out.println(this.getDate());
            date = new java.sql.Date(this.getDate().getTime());//(java.sql.Date)this.getDate();
        }
        return date;
    }

    public String getAdress() {
        return adress.get();
    }
    public StringProperty adressProperty() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress.set(adress);
    }
    public String getNote() {
        return note.get();
    }
    public StringProperty noteProperty() {
        return note;
    }
    public void setNote(String note) {
        this.note.set(note);
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
    public java.util.Date getDate() {
        return new java.util.Date(date.getTime());
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public PLC getPlc() {
        return plc;
    }
    public void setPlc(PLC plc) {
        this.plc = plc;
    }
    public Iniciator getIniciator() {
        return iniciator;
    }
    public void setIniciator(Iniciator iniciator) {
        this.iniciator = iniciator;
    }
    public Engineer getEngineer() {
        return engineer;
    }
    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }
//    public String getPLC() {
//        return this.plc;
//    }
//    public StringProperty PLCProperty() {
//        return PLC;
//    }
//    public void setPLC(String PLC) {
//        this.PLC.set(PLC);
//    }
//    public int getEngineer_id() {
//        return this.engineer.ge engineer_id.get();
//    }
//    public IntegerProperty engineer_idProperty() {
//        return engineer_id;
//    }
//    public void setEngineer_id(int engineer_id) {
//        this.engineer_id.set(engineer_id);
//    }
//    public int getIniciator_id() {
//        return iniciator_id.get();
//    }
//    public IntegerProperty iniciator_idProperty() {
//        return iniciator_id;
//    }
//    public void setIniciator_id(int iniciator_id) {
//        this.iniciator_id.set(iniciator_id);
//    }
//    public int getPLC_id() {
//        return PLC_id.get();
//    }
//    public int PLC_idProperty() {
//        return PLC_id.get();
//    }
//    public void setPLC_id(int PLC_id) {
//        this.PLC_id.set(PLC_id);
//    }
//    public String getEngineerName() {
//        return engineerName.get();
//    }
//    public StringProperty engineerNameProperty() {
//        return engineerName;
//    }
//    public void setEngineerName(String engineerName) {
//        this.engineerName.set(engineerName);
//    }
//    public String getInitiator_name() {
//        return initiator_name.get();
//    }
//    public StringProperty initiator_nameProperty() {
//        return initiator_name;
//    }
//    public void setInitiator_name(String initiator_name) {
//        this.initiator_name.set(initiator_name);
//    }

//    public void updateFromMap() {
//        this.setAdress((String) super.varMap.get(adressTitle));
//        this.setNote((String) super.varMap.get(noteTitle));
//        this.setDate((Date)super.varMap.get(dateTitle));
//        this.setEngineer((Engineer) super.varMap.get(engineerTitle));
//        this.setIniciator((Iniciator) super.varMap.get(iniciatorTitle));
//        this.setPlc((PLC) super.varMap.get(plcTitle));
//    }
}
