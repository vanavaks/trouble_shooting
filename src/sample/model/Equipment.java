package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Иван on 16.07.2016.
 */
@Table(title = "Оборудование", table = "equipment")
public class Equipment extends dialogableModelDB{
    @Column(title = "Название", column = "name")
    @NotNull(message = "Введите Название")
    public String name;
    @Column(title = "Позиция", column = "position")
    @NotNull(message = "Укажите позицию")
    public String position;
    @Column(title = "Подзона", column = "subZone_id")
    public SubZone subZone;
    @Column(title = "Прибор", column = "instrment_id")
    public Instrument instrment;
    @Column(title = "Описание", column = "note")
    public String note;
    @Column(title = "Назначение", column = "appointment")
    public String appointment;
    @Column(title = "Страница на схеме", column = "schematicDiagramPage")
    public String schematicDiagramPage;

    public Equipment(int id, String name, String position, int subZone_id, int instrment_id, String note, String appointment, String schematicDiagramPage) {
        super.setId(id);
        this.name = name;
        this.position = position;
        this.subZone = new SubZone().get(subZone_id);
        this.instrment = new Instrument().get(instrment_id);
        this.note = note;
        this.appointment = appointment;
        this.schematicDiagramPage = schematicDiagramPage;
    }
    //public Equipment(String name) {this(); this.name = name;}
    public Equipment() {
        this(0, "", "", 0, 0, "", "", "");
    }
    public Equipment(String name, String pos, SubZone subZone){
        this(); this.name = name; this.position = pos; this.subZone = subZone;
    }
    public Equipment(String name, String pos, SubZone subZone, String note){
        this(name, pos, subZone);
        this.note = note;
    }
//    CREATE TABLE `troubleshooting`.`equipment` (
//            `id` INT NOT NULL AUTO_INCREMENT,
//    `name` VARCHAR(45) NULL,
//    `position` VARCHAR(45) NULL,
//    `subZone_id` INT NULL,
//    `instrment_id` INT NULL,
//    `note` VARCHAR(45) NULL,
//    `appointment` VARCHAR(45) NULL,
//    `schematicDiagramPage` VARCHAR(45) NULL,
//    PRIMARY KEY (`id`),
//    UNIQUE INDEX `idequipment_UNIQUE` (`id` ASC));

    public ObservableList<Equipment> getData(){
        return Equipment.getDataFiltered(null,null);
//        ObservableList<Equipment> list = FXCollections.observableArrayList();
//        try{
//            Statement statement = getConnection().createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT * " +
//                            "FROM troubleshooting.equipment "
//            );
//            while (resultSet.next()) {
//                list.add(
//                        new Equipment(  resultSet.getInt("id"),
//                                resultSet.getNString("name"),
//                                resultSet.getNString("position"),
//                                resultSet.getInt("subZone_id"),
//                                resultSet.getInt("instrment_id"),
//                                resultSet.getNString("note"),
//                                resultSet.getNString("appointment"),
//                                resultSet.getNString("schematicDiagramPage")
//                        )
//
//                );
//            }
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//        return list;
    }


    public static ObservableList<Equipment> getDataFiltered(SubZone subZone, Instrument instrument){
        ObservableList<Equipment> list = FXCollections.observableArrayList();
        String query =  "SELECT * " +
                        "FROM troubleshooting.equipment ";
        if(subZone != null){
            query += " WHERE `subZone_id`=" + subZone.getId();
            if(instrument != null) {
                query += " AND `instrment_id`=" + instrument.getId();
            }
        }
        else if(instrument != null){
            query += " WHERE `instrment_id`=" + instrument.getId();
        }
        query += " order by position";
        System.out.println(query);
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery( query );
            while (resultSet.next()) {
                list.add(
                        new Equipment(  resultSet.getInt("id"),
                                resultSet.getNString("name"),
                                resultSet.getNString("position"),
                                resultSet.getInt("subZone_id"),
                                resultSet.getInt("instrment_id"),
                                resultSet.getNString("note"),
                                resultSet.getNString("appointment"),
                                resultSet.getNString("schematicDiagramPage")
                        )
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static Equipment get(int id){
        String query =  "SELECT * " +
                "FROM troubleshooting.equipment " +
                "WHERE `id`=" + id;
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery( query );
            if(resultSet.next()){
                return new Equipment(  resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("position"),
                                    resultSet.getInt("subZone_id"),
                                    resultSet.getInt("instrment_id"),
                                    resultSet.getNString("note"),
                                    resultSet.getString("appointment"),
                                    resultSet.getString("schematicDiagramPage")
                            );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public boolean delete(){
        if(Trouble.getDataFiltered(this, null).size() == 0){
            return super.delete();
        }
        return false;
    }

    public String toString(){
        return position + " " + name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public SubZone getSubZone() {
        return subZone;
    }
    public void setSubZone(SubZone subZone) {
        this.subZone = subZone;
    }
    public Instrument getInstrment() {
        if (instrment == null){
            return new Instrument();
        }
        return instrment;
    }
    public void setInstrment(Instrument instrment) {
        this.instrment = instrment;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getAppointment() {
        return appointment;
    }
    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
    public String getSchematicDiagramPage() {
        return schematicDiagramPage;
    }
    public void setSchematicDiagramPage(String schematicDiagramPage) {
        this.schematicDiagramPage = schematicDiagramPage;
    }
}
