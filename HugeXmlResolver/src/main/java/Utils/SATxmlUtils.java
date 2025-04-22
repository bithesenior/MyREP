package Utils;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author oooooooooooldbi
 * @date 2025/4/19 10:38
 * @email bithesenior@163.com
 */

public class SATxmlUtils {

    public static List readXMLToEntity(String xmlFilePath, String entityPackage)  {
        try{
            FileInputStream xmlStream = null;
            xmlStream=new FileInputStream(xmlFilePath);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            MySATHandler handler = new MySATHandler(entityPackage);

            parser.parse( xmlStream, handler );
            xmlStream.close();

            List resultList = handler.getResultList();

            return resultList;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }


    public static List readZipToEntity(String zipFilePath, String entityPackage) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            MySATHandler handler = new MySATHandler(entityPackage);

            ZipFile zip = new ZipFile(new File(zipFilePath));
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                InputStream xmlStream = zip.getInputStream(entry);
                parser.parse( xmlStream, handler );
                xmlStream.close();
            }

            List resultList = handler.getResultList();
            return resultList;

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List readStreamToEntity(InputStream xmlStream, String entityPackage) {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            MySATHandler handler = new MySATHandler(entityPackage);

            parser.parse( xmlStream, handler );
            xmlStream.close();

            List resultList = handler.getResultList();
            return resultList;
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
