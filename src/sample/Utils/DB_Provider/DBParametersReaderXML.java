package sample.Utils.DB_Provider;

import sample.Main;
import sample.Utils.Logger.LoggerHelper;
import sample.Utils.XML_parser.IParser;
import sample.Utils.XML_parser.JaxbParser;
import sample.controller.MainController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by VAN on 20.09.2016.
 */
public class DBParametersReaderXML implements IDBParametersReader{
    //LoggerFX log = new LoggerFX("DBParametersReaderXML");
    Logger log = LoggerHelper.getMainControllerLogger(); // Logger.getLogger(MainController.class.getName());
    private static String fileName = "Config/DBconfig.xml";
    private static String dirName = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();         //directory trouble_shooting
    public DBParametersReaderXML(String fileName, String dirName) {
        DBParametersReaderXML.fileName = fileName;
        DBParametersReaderXML.dirName = dirName;
    }

    public DBParametersReaderXML() {
    }

    @Override
    public DBConnProp read() throws JAXBException {
        log.fine("initializing - trying to load DataBase configuration file from \r\n " + dirName + fileName + " ...");
        JaxbParser parser = new JaxbParser();
        File file = new File(new File(dirName),fileName);
        DBConnProp prop = (DBConnProp)parser.getObject(file,DBConnProp.class);
        return prop;
    }

    @Override
    public void write(DBConnProp prop) throws Exception {
        log.fine("initializing - trying to save DataBase configuration file from \r\n " + dirName + fileName + " ...");
        JaxbParser parser = new JaxbParser();
        File file = new File(new File(dirName),fileName);
        parser.saveObject(file, prop);
    }
}
