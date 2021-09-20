package com.ss.lms.models;

public class BookGenres implements IModel {
    private Genre genre;
    private Book book;

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    @Override
    public String getMenuRep() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getShortName() { return "Book Genre"; }
}
