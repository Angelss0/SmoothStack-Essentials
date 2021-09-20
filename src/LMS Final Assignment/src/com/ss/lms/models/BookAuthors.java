package com.ss.lms.models;

public class BookAuthors implements IModel {
    private Book book;
    private Author author;

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    @Override
    public String getMenuRep() {
        // TODO Auto-generated method stub
        return null;
    }
        
    @Override
    public String getShortName() { return "Book Author"; }
}
