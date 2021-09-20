package com.ss.lms.models;

import java.sql.Date;

public class BookLoans implements IModel {
    private Book book;
    private LibraryBranch branch;
    private Borrower borrower;
    private Date dateOut;
    private Date dueDate;
    private Date dateIn;

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LibraryBranch getBranch() { return branch; }
    public void setBranch(LibraryBranch branch) { this.branch = branch; }

    public Borrower getBorrower() { return borrower; }
    public void setBorrower(Borrower borrower) { this.borrower = borrower; }

    public Date getDateOut() { return dateOut; }
    public void setDateOut(Date dateOut) { this.dateOut = dateOut; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getDateIn() { return dateIn; }
    public void setDateIn(Date dateIn) { this.dateIn = dateIn; }
        
    @Override
    public String getMenuRep() {
        // TODO Auto-generated method stub
        return null;
    }
        
    @Override
    public String getShortName() { return "Book loan"; }
}
