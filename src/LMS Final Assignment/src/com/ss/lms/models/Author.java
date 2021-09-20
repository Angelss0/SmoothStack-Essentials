package com.ss.lms.models;

import java.util.List;

public class Author implements IModel {
    private int id;
    private String name;
    private List<BookAuthors> bookAuthors;

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BookAuthors> getBookAuthors() { return bookAuthors; }
    public void setBookAuthors(List<BookAuthors> bookAuthors) { this.bookAuthors = bookAuthors; }
    
    @Override
    public String getMenuRep() { return getName(); }
}
