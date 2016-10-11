package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Utils.Validator.NotNull;
import sample.model.Mothers.*;

import java.sql.*;

/**
 * Created by Иван on 08.04.2016.
 */
@Table(title = "Инженер", table = "engineer")
public class Engineer extends dialogableModelDB {
    @Column(title = "Имя", column = "firstName")
    @NotNull(message = "Введите имя")
    public String firstName;
    @Column(title = "Фамилия", column = "lastName")
    @NotNull(message = "Введите Фамилию")
    public String lastName;
    @Column(title = "Смена", column = "shift")
    public String shift;

    public Engineer(){this(0," ", " "," ");}
    public Engineer(int id, String firstName, String lastName, String shift){
        super.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.shift = shift;

//        varMap = new HashMap<String, Object>();
//        super.varMap.put(firstNameTitle,this.getFirstName());
//        super.varMap.put(lastNameTitle,this.getLastName());
//        super.varMap.put(shiftTitle,this.getShift());
    }

    public ObservableList<Engineer> getData(){
        ObservableList<Engineer> list = FXCollections.observableArrayList();
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.engineer " /*+
                            "where id = 1;"*/ +
                            " order by lastName "
            );
            while (resultSet.next()) {
                if(resultSet.getInt("id") ==0) continue;

                list.add(
                        new Engineer(   resultSet.getInt("id"),
                                        resultSet.getString("firstName"),
                                        resultSet.getString("lastName"),
                                        resultSet.getNString("shift")
                        )

                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static Engineer get(int id){
        Engineer eng = null;
        try {
            Statement statement = ModelDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * " +
                            "FROM troubleshooting.engineer " +
                            "where id = " + id + ";"
            );
            if (resultSet.next()) {
                eng = new Engineer(   resultSet.getInt("id"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getNString("shift")
                );
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return eng;
    }

    @Override
    public boolean delete()throws SQLException{
        ObservableList<Trouble> list = Trouble.getDataFiltered(null,this);
        if(list.size() == 0){
            return super.delete();
        }
        return false;
    }

//    public void insert(){
//        //INSERT INTO `troubleshooting`.`engineer` (`id`, `firstName`, `lastName`, `shift`) VALUES ('4', 'Leniv', 'Vitaliy', 'C');
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "INSERT INTO `troubleshooting`.`engineer` (`id`, `firstName`, `lastName`, `shift`) " +
//                    "VALUES (?, ?, ?, ?);"
//            );
//            ps.setInt(1, 0);
//            ps.setString(2, this.getFirstName());
//            ps.setString(3, this.getLastName());
//            ps.setString(4, this.getShift());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void delete(){
//        //DELETE FROM `troubleshooting`.`engineer` WHERE `id`='3';
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "DELETE FROM `troubleshooting`.`engineer` " +
//                    "WHERE `id`=?;"
//            );
//            ps.setInt(1, this.getId());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public void update(){
//        //UPDATE `troubleshooting`.`engineer` SET `firstName`='Ruslan1', `lastName`='Galickyi2', `shift`='vf' WHERE `id`='2';
//        try{
//            PreparedStatement ps = ModelDB.getConnection().prepareStatement(
//                    "UPDATE `troubleshooting`.`engineer` " +
//                    "SET `firstName`=?, " +
//                    "`lastName`=?, " +
//                    "`shift`=? " +
//                    "WHERE `id`=?;"
//            );
//            ps.setString(1, this.getFirstName());
//            ps.setString(2, this.getLastName());
//            ps.setString(3, this.getShift());
//            ps.setInt(4, this.getId());
//            ps.executeUpdate();
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }


    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getShift() {
        return shift;
    }
    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override public String toString(){
        return firstName + " " + lastName;
    }

    public dialogableModelDB getTest(){
        return this;
    }


}
