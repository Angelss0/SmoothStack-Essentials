package com.ss.lms.models;

import java.util.List;

public class Genre implements IModel {
    private int id;
    private String name;
    private List<BookGenres> bookGenres;

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BookGenres> getBookGenres() { return bookGenres; }
    public void setBookGenres(List<BookGenres> bookGenres) { this.bookGenres = bookGenres; }
        
    @Override
    public String getMenuRep() {
        // TODO Auto-generated method stub
        return null;
    }
        
    @Override
    public String getShortName() { return "Genre"; }
}
