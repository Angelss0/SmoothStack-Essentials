package com.ss.lms.models;

import java.util.List;

public class LibraryBranch implements IModel {
    private int id;
    private String name;
    private String address;
    private List<BookLoans> bookLoans;
    private List<BookCopies> bookCopies;

    public int getID() { return id; }
    public void setID(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<BookLoans> getBookLoans() { return bookLoans; }
    public void setBookLoans(List<BookLoans> bookLoans) { this.bookLoans = bookLoans; }

    public List<BookCopies> getBookCopies() { return bookCopies; }
    public void setBookCopies(List<BookCopies> bookCopies) { this.bookCopies = bookCopies; }

    @Override
    public String toString() {
        return "Branch - ID: " + getID() +
        ", name: " + getName() +
        ", address:" + getAddress();
    }

    @Override
    public String getMenuRep() {
        return getName() + ", " + getAddress();
    }
        
    @Override
    public String getShortName() { return "Library Branch"; }
}
