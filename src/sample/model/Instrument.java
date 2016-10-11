package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Mothers.Column;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.Table;
import sample.model.Mothers.dialogableModelDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Иван on 16.07.2016.
 */
@Table(title = "Прибор", table = "instrument")
public class Instrument extends dialogableModelDB {
    @Column(title = "Тип", column = "type")
    @NotNull(message = "Введите тип прибора")
    public String name;
    @Column(title = "Приозводитель", column = "manufacturer_id")
    public Manufacturer manufacturer;
    @Column(title = "Функция", column = "function_id")
    public InstrumentFunc function;
    @Column(title = "Количество Запасных", column = "spare")
    @NotNull(message = "Введите количество запасных")
    public Integer spare;
    @Column(title = "Примечание", column = "note")
    public String note;
    @Column(title = "Заказной номер", column = "orderNumber")
    @NotNull(message = "Введите заказной номер")
    public String orderNumber;
    @Column(title = "Параметры", column = "parameters")
    public String parameters;
    @Column(title = "Степень важности", column = "importancy")// 1 .. 5
    public Integer importancy;


    private ObservableList<InstrumentFunc> instrumentTypeslist;


    public Instrument(int id, String type, int manufacturer_id, int function_id, int spare, String note, String orderNumber, String parameters, int importancy) {
        super.setId(id);
        this.name = type;
        this.manufacturer = new Manufacturer().get(manufacturer_id);
        this.function = new InstrumentFunc().get(function_id);
        this.spare = spare;
        this.note = note;
        this.orderNumber = orderNumber;
        this.parameters = parameters;
        this.importancy = importancy;
    }
    public Instrument(){
        this(0,"",0,0,0,"","","",0);
        this.manufacturer = new Manufacturer().get(0);
        this.function = new InstrumentFunc().get(0);
    }
    public Instrument(String name, String note,Manufacturer m, InstrumentFunc f){
        this();
        this.name=name;
        this.note=note;
        if(f!=null) this.function = f;
        if(m!=null) this.manufacturer = m;
    }
    public ObservableList<Instrument> getData(){
        return getDataFiltered(null, null);
    }
    public static ObservableList<Instrument> getDataFiltered(InstrumentFunc instrument, Manufacturer manufacturer){
        ObservableList<Instrument> list = FXCollections.observableArrayList();
        String query = "SELECT * " +
                "FROM troubleshooting.instrument ";
        if(instrument != null){
            query += " WHERE `function_id`=" + instrument.getId();
            if(manufacturer != null){
                query += " and `manufacturer_id`=" + manufacturer.getId();
            }
        }
        else if(manufacturer != null){
            query += " WHERE `manufacturer_id`=" + manufacturer.getId();
        }
        query += " order by function_id";
        System.out.println(query);
        try{
            Statement statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if(resultSet.getInt("id") == 0) continue;
                list.add(
                        new Instrument(  resultSet.getInt("id"),
                                resultSet.getNString("type"),
                                resultSet.getInt("manufacturer_id"),
                                resultSet.getInt("function_id"),
                                resultSet.getInt("spare"),
                                resultSet.getNString("note"),
                                resultSet.getNString("orderNumber"),
                                resultSet.getNString("parameters"),
                                resultSet.getInt("importancy")
                        )

                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public Instrument get(int id){
    try{
        Statement statement = getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT * " +
                        "FROM troubleshooting.instrument " +
                        "where id = " + id + ";"
        );
        if (resultSet.next()) {
            return new Instrument(  resultSet.getInt("id"),
                                    resultSet.getNString("type"),
                                    resultSet.getInt("manufacturer_id"),
                                    resultSet.getInt("function_id"),
                                    resultSet.getInt("spare"),
                                    resultSet.getNString("note"),
                                    resultSet.getNString("orderNumber"),
                                    resultSet.getNString("parameters"),
                                    resultSet.getInt("importancy")
            );
        }
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    return null;
}
    @Override
    public boolean delete()throws SQLException{
        ObservableList<Equipment> list = Equipment.getDataFiltered(null,this);
        if(list.size() == 0) return super.delete();
        return false;
    }
    @Override
    public String toString(){
        return manufacturer + " " + name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Manufacturer getManufacturer() {
        return manufacturer;
    }
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }
    public InstrumentFunc getFunction() {
        return function;
    }
    public void setFunction(InstrumentFunc function) {
        this.function = function;
    }
    public int getSpare() {
        return spare;
    }
    public void setSpare(int spare) {
        this.spare = spare;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getParameters() {
        return parameters;
    }
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
    public int getImportancy() {
        return importancy;
    }
    public void setImportancy(int importancy) {
        this.importancy = importancy;
    }
}
