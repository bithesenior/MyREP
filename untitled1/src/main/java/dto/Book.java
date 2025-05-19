package dto;
import anos.FieldXMLElement;
import anos.ObjectXMLElement;

import java.util.ArrayList;
import java.util.List;

@ObjectXMLElement(addMethod = "addBook",rootMethod = "resolveBysingle")
public class Book {

    @FieldXMLElement
    String id;
    @FieldXMLElement
    String name;
    @FieldXMLElement
    String author;
    @FieldXMLElement(setMethod = "setYearrrrrrr")
    String year;
    @FieldXMLElement(name = "price")
    String bookPrice;

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

    public void setYearrrrrrr(String year) {
        this.year = year;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }
}
