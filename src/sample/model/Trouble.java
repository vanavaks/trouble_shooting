package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.FX.FXHelper;
import sample.model.Mothers.Column;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.Table;
import sample.model.Mothers.dialogableModelDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Formatter;

/**
 * Created by Иван on 23.06.2016.
 */
@Table(title = "Неисправность", table = "trouble")
public class Trouble extends dialogableModelDB {
    @Column(title = "Дата", column = "date")
    public Date date;
    @Column(title = "Описание неисправности", column = "note")
    @NotNull(message = "Опишите неисправность")
    public String note;
    @Column(title = "Причина", column = "reason")
    public String reason;
    @Column(title = "Оборудвание", column = "equipment_Id")
    public Equipment equipment;
    @Column(title = "Примечание", column = "actions")
    @NotNull(message = "Опишите произведенные мероприятия")
    public String actions;
    @Column(title = "Ремонт на остановке", column = "PPR")
    public Boolean ppr;
    @Column(title = "Kоличество", column = "number")
    public Integer number;
    @Column(title = "Инженер", column = "engineer_id")
    public Engineer engineer;

    public Trouble(Integer id, Date date, String note, String reason, int equipment_id, String actions, Boolean ppr, Integer number,int engineer_id) {
        super.setId(id);
        this.setDate(new java.util.Date(date.getTime()));
        this.note = note;
        this.reason = reason;
        this.equipment = Equipment.get(equipment_id);
        this.actions = actions;
        this.ppr = ppr;
        this.number = number;
        this.engineer = Engineer.get(engineer_id);
    }
    public Trouble(){
        this(0,new Date(),"","",0,"",false,1,0);
    }

//    @Column(title = "Неисправный узел", column = "instrument_id")
//    private Instrument instrument;
//    @Column(title = "Время", column = "time")
//    private Time time;
//    @Column(title = "Ошибка", column = "fault")
//    private String Fault;

    public ObservableList<Trouble> getData(){
        return getDataFiltered(null,null);
    }
    public static Trouble get(int id){
        String query =  "SELECT * " +
                "FROM troubleshooting.trouble " +
                "WHERE `id`=" + id;
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery( query );
            if(resultSet.next()){
                return new Trouble( resultSet.getInt("id"),
                        resultSet.getDate("date"),
                        resultSet.getNString("note"),
                        resultSet.getNString("reason"),
                        resultSet.getInt("equipment_id"),
                        resultSet.getNString("actions"),
                        resultSet.getBoolean("PPR"),
                        resultSet.getInt("number"),
                        resultSet.getInt("engineer_id")
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static ObservableList<Trouble> getDataFiltered(Equipment equipment,Engineer engineer){
        ObservableList<Trouble> list = FXCollections.observableArrayList();
        String query =  "SELECT * " +
                "FROM troubleshooting.trouble ";
        if(equipment != null) {
            query += " WHERE `equipment_Id`=" + equipment.getId();
            if(engineer != null){
                query += " , `engineer_id`=" + engineer.getId();
            }
        }
        else if(engineer != null){
            query += " WHERE `engineer_id`=" + engineer.getId();
        }

        query += " order by date";
        System.out.println(query);
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery( query );
            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                list.add(
                        new Trouble( resultSet.getInt("id"),
                                resultSet.getDate("date"),
                                resultSet.getNString("note"),
                                resultSet.getString("reason"),
                                resultSet.getInt("equipment_Id"),
                                resultSet.getString("actions"),
                                resultSet.getBoolean("PPR"),
                                resultSet.getInt("number"),
                                resultSet.getInt("engineer_id")
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
        ObservableList list = Force.getDataFiltered(this);
        if(list.size() == 0){
            return super.delete();
        }
        return false;
    }

    @Override
    public String toString(){
        return FXHelper.dateFormatted(getDate()) + " " + note + " " + getEquipment().toString();
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public Equipment getEquipment() {
        return equipment;
    }
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
    public String getActions() {
        return actions;
    }
    public void setActions(String actions) {
        this.actions = actions;
    }
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public void setEngineer(Engineer engineer) {
        this.engineer = engineer;
    }

    public Boolean getPpr() {
        return ppr;
    }

    public void setPpr(Boolean ppr) {
        this.ppr = ppr;
    }
}
