package sample.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Иван on 07.07.2016.
 */
public class Model {
    protected static List<Object> editList = new ArrayList<Object>();
    protected static Connection connection;

    public static List<Object> getEditList() {
        return editList;
    }
    public static void setConnection(Connection connection) {
        Model.connection = connection;
    }

}
