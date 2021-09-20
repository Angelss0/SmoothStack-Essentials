package com.ss.lms.controllers;

import com.ss.lms.models.Book;
import com.ss.lms.models.BookCopies;
import com.ss.lms.models.LibraryBranch;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookCopiesController extends Controller<BookCopies> {

    BookController bookController = new BookController();
    LibraryBranchController branchController = new LibraryBranchController();

    public BookCopiesController() {
        super();
        tableName = "tbl_book_copies";
    }

    @Override
    public void add(BookCopies bookCopy) throws SQLException {
        add(new Object[] {
            bookCopy.getBook().getBookID(),
            bookCopy.getBranch().getID(),
            bookCopy.getNoOfCopies()
        });
    }

    @Override
    public void delete(BookCopies bookCopy) throws SQLException {
        delete(new String[] {
            "bookId",
            "branchId"
        }, new Object[] {
            bookCopy.getBook().getBookID(),
            bookCopy.getBranch().getID()
        });
    }

    @Override
    protected BookCopies extractModel(ResultSet rs) throws SQLException {
        BookCopies bookCopy = new BookCopies();
        bookCopy.setBook(bookController.find(rs.getInt("bookId")));
        bookCopy.setBranch(branchController.find(rs.getInt("branchId")));
        bookCopy.setNoOfCopies(rs.getInt("noOfCopies"));
        return bookCopy;
    }

    public BookCopies find(Book book, LibraryBranch branch) throws SQLException {
        return read(new String[] {
            "bookId",
            "branchId"
        }, new Object[] {
            book.getBookID(),
            branch.getID()
        });
    }

    public void updateBook(BookCopies bookCopy, Book book) throws SQLException {
        update(new String[] {
            "bookId"
        }, new String[] {
            "bookId",
            "branchId"
        }, new Object[] {
            book.getBookID(),
            bookCopy.getBook().getBookID(),
            bookCopy.getBranch().getID()
        });
    }

    public void updateBranch(BookCopies bookCopy, LibraryBranch branch) throws SQLException {
        update(new String[] {
            "branchId"
        }, new String[] {
            "bookId",
            "branchId"
        }, new Object[] {
            branch.getID(),
            bookCopy.getBook().getBookID(),
            bookCopy.getBranch().getID()
        });
    }
    
    public void updateNoOfCopies(BookCopies bookCopy, int noOfCopies) throws SQLException {
        update(new String[] {
            "noOfCopies"
        }, new String[] {
            "bookId",
            "branchId"
        }, new Object[] {
            noOfCopies,
            bookCopy.getBook().getBookID(),
            bookCopy.getBranch().getID()
        });
    }

    public void addCopies(BookCopies bookCopy, int copies) throws SQLException {
        updateNoOfCopies(bookCopy, bookCopy.getNoOfCopies() + copies);
    }

    public int removeCopies(BookCopies bookCopy, int copies) throws SQLException {
        if (bookCopy.getNoOfCopies() - copies < 0) {
            System.out.println("Error: More copies have been removed than owned!");
            return -1;
        }
        
        updateNoOfCopies(bookCopy, bookCopy.getNoOfCopies() - copies);
        return copies;
    }
}
