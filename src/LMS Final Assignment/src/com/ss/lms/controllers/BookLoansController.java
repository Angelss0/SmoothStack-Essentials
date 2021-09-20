package com.ss.lms.controllers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Book;
import com.ss.lms.models.BookLoans;
import com.ss.lms.models.Borrower;
import com.ss.lms.models.LibraryBranch;

public class BookLoansController extends Controller<BookLoans> {

    public BookLoansController() {
        super();
        tableName = "tbl_book_loans";
    }

    @Override
    public void add(BookLoans bookLoan) throws SQLException {
        add(new Object[] {
            bookLoan.getBook().getBookID(),
            bookLoan.getBranch().getID(),
            bookLoan.getBorrower().getCardNo(),
            bookLoan.getDateOut(),
            bookLoan.getDueDate()
        });
    }

    @Override
    public void delete(BookLoans bookLoan) throws SQLException {
        delete(new String[] {
            "bookId",
            "branchId",
            "cardNo"
        }, new Object[] {
            bookLoan.getBook().getBookID(),
            bookLoan.getBranch().getID(),
            bookLoan.getBorrower().getCardNo(),
        });
    }

    @Override
    protected BookLoans extractModel(ResultSet rs) throws SQLException {
        BookLoans bookLoan = new BookLoans();
        
        BookController bookController = new BookController();
        LibraryBranchController branchController = new LibraryBranchController();
        BorrowerController borrowerController = new BorrowerController();

        bookLoan.setBook(bookController.find(rs.getInt("bookId")));
        bookLoan.setBranch(branchController.find(rs.getInt("branchId")));
        bookLoan.setBorrower(borrowerController.find(rs.getInt("cardNo")));
        bookLoan.setDateOut(rs.getDate("dateOut"));
        bookLoan.setDueDate(rs.getDate("dueDate"));
        return bookLoan;
    }

    public BookLoans find(Book book, LibraryBranch branch, Borrower borrower) throws SQLException {
        return read(new String[] {
            "bookId",
            "branchId",
            "cardNo"
        }, new Object[] {
            book.getBookID(),
            branch.getID(),
            borrower.getCardNo(),
        });
    }

    public void updateDueDate(BookLoans bookLoan, Date dueDate) throws SQLException {
        update(new String[] {
            "dueDate",
        }, new String[] {
            "bookId",
            "branchId",
            "cardNo"
        }, new Object[] {
            dueDate,
            bookLoan.getBook().getBookID(),
            bookLoan.getBranch().getID(),
            bookLoan.getBorrower().getCardNo()
        });
    }
}
