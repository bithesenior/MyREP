import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


public static void main(String[] args) {

    List<Book> books = new ArrayList<Book>();
            try {
                XMLInputFactory factory = XMLInputFactory.newInstance();
                XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("D:\\demo\\untitled\\src\\books.xml"));

                while (reader.hasNext()) {
                    int event = reader.next();

                    if (event == XMLEvent.START_ELEMENT) {
                        String elementName = reader.getLocalName();
                        if ("book".equals(elementName)) {
                            Book book = new Book();
                            String id = reader.getAttributeValue(null, "id");
                            book.setId(Integer.parseInt(id));
                            System.out.println("Book ID: " + id);
                        } else if ("title".equals(elementName)) {
                            System.out.println("Title: " + reader.getElementText());
                        } else if ("author".equals(elementName)) {
                            System.out.println("Author: " + reader.getElementText());
                        } else if ("price".equals(elementName)) {
                            System.out.println("Price: " + reader.getElementText());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


