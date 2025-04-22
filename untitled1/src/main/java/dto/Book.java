package dto;
import anos.FieldXMLElement;
import anos.ObjectXMLElement;

import java.util.ArrayList;
import java.util.List;

@ObjectXMLElement(name ="book",addMethod = "addBook",rootMethod ="resolveBysingle")
public class Book {

    @FieldXMLElement(name = "id",setMethod = "setId")
    String id;
    @FieldXMLElement(name = "name",setMethod = "setName")
    String name;
    @FieldXMLElement(name = "author",setMethod = "setAuthor")
    String author;
    @FieldXMLElement(name = "year",setMethod = "setYear")
    String year;
    @FieldXMLElement(name = "price",setMethod = "setPrice")
    String price;

    Language language;

    List<Page> pages = new ArrayList<Page>();

    public void resolveBysingle(){
        System.out.println("addToDatabase"+this.id);
    }

    public void addPage(Page page) {
        pages.add(page);
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Book() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String prise) {
        this.price = prise;
    }

}
