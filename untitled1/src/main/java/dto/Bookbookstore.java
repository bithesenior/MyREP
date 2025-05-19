package dto;

import anos.ObjectXMLElement;

import java.util.ArrayList;
import java.util.List;
@ObjectXMLElement(name ="bookstore")
public class Bookbookstore {

    Holder holder;

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    List<Book> books = new ArrayList<Book>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public  void addBook(Book book) {
        books.add(book);
    }
}
