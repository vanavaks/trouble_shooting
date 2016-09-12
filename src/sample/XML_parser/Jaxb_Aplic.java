package sample.XML_parser;


import sample.MySQLConnection;

import java.io.File;

/**
 * Created by Иван on 12.08.2016.
 */
public class Jaxb_Aplic {
    private JaxbParser parser;
    private File file;

    public void setUp() throws Exception {
        parser = new JaxbParser();
        file = new File("config.xml");
    }

    public  MySQLConnection XML_readSqlConnection() throws Exception {
        MySQLConnection connection = new MySQLConnection();
        connection = (MySQLConnection)parser.getObject(file,MySQLConnection.class);
        connection.establishConnection();
        return connection;
    }
}
