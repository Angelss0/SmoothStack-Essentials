package com.ss.lms.models;

public class BookCopies implements IModel {
    private Book book;
    private LibraryBranch branch;
    private int noOfCopies;

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LibraryBranch getBranch() { return branch; }
    public void setBranch(LibraryBranch branch) { this.branch = branch; }

    public int getNoOfCopies() { return noOfCopies; }
    public void setNoOfCopies(int noOfCopies) { this.noOfCopies = noOfCopies; }

    @Override
    public String getMenuRep() {
        return getBook().getTitle() + " by " +
        (getBook().getAuthor() != null ? getBook().getAuthor().getName() : "N/A")
        + " (" + getNoOfCopies() + ")";
    }
}
