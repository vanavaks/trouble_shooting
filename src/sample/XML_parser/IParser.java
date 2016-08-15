package sample.XML_parser;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by Иван on 12.08.2016.
 */
public interface IParser {
    Object getObject(File file, Class c) throws JAXBException;
    void saveObject(File file, Object o) throws JAXBException;
}
