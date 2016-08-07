package sample.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.Date;

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

    public Trouble(Integer id, Date date, String note, String reason, int equipment_id, String actions, Boolean ppr, Integer number) {
        super.setId(id);
        this.date = new java.util.Date(date.getTime());
        this.note = note;
        this.reason = reason;
        this.equipment = Equipment.get(equipment_id);
        this.actions = actions;
        this.ppr = ppr;
        this.number = number;
    }
    public Trouble(){
        this(0,new Date(),"","",0,"",false,1);
    }

//    @Column(title = "Неисправный узел", column = "instrument_id")
//    private Instrument instrument;
//    @Column(title = "Время", column = "time")
//    private Time time;
//    @Column(title = "Ошибка", column = "fault")
//    private String Fault;

    public ObservableList<Trouble> getData(){
        return getDataFiltered(null);
    }
    public Trouble get(int id){
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
                        resultSet.getInt("number")
                        );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static ObservableList<Trouble> getDataFiltered(Equipment equipment){
        ObservableList<Trouble> list = FXCollections.observableArrayList();
        String query =  "SELECT * " +
                "FROM troubleshooting.trouble ";
        if(equipment != null) {
            query += " WHERE `subZone_id`=" + equipment.getId();
        }
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
                                resultSet.getInt("number")
                        )
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
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
}
