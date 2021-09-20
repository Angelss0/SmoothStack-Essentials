package com.ss.lms.models;

import java.util.List;

public class Book implements IModel {
    private int bookID;
    private String title;
    private Author author;
    private Publisher publisher;
    private List<BookAuthors> authors;
    private List<BookGenres> genres;

    public int getBookID() { return bookID; }
    public void setBookID(int bookID) { this.bookID = bookID; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }

    public List<BookAuthors> getAuthors() { return authors; }
    public void setAuthors(List<BookAuthors> authors) { this.authors = authors; }

    public List<BookGenres> getGenres() { return genres; }
    public void setGenres(List<BookGenres> genres) { this.genres = genres; }

    @Override
    public String toString() {
        return "  " + getTitle() + "\n  "
        + (getAuthor() != null ? getAuthor().getMenuRep() : "No author") + "\n  "
        + (getGenres() != null ? getGenres().toString() : "no genres") + "\n  "
        + (getPublisher() != null ? getPublisher().getName() : "no publisher") + "\n";
    }

    @Override
    public String getMenuRep() {
        return getTitle() + " by " + (getAuthor() != null ? getAuthor().getName() : "N/A");
    }
}
