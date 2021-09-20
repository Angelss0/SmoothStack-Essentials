package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms.models.Author;
import com.ss.lms.models.Book;
import com.ss.lms.models.BookAuthors;

public class BookAuthorsController extends Controller<BookAuthors> {

    BookController bookController = new BookController();
    AuthorController authorController = new AuthorController();

    public BookAuthorsController() {
        super();
        tableName = "tbl_book_authors";
    }

    @Override
    public void add(BookAuthors bookAuthor) throws SQLException {
        add(new Object[] {
            bookAuthor.getBook().getBookID(),
            bookAuthor.getAuthor().getID(),
        });
    }

    @Override
    public void delete(BookAuthors bookAuthor) throws SQLException {
        delete(new String[] {
            "bookId",
            "authorId"
        }, new Object[] {
            bookAuthor.getBook(),
            bookAuthor.getAuthor(),
        });
    }
    
    @Override
    protected BookAuthors extractModel(ResultSet rs) throws SQLException {
        BookAuthors bookAuthors = new BookAuthors();
        bookAuthors.setBook(bookController.find(rs.getInt("bookId")));
        bookAuthors.setAuthor(authorController.find(rs.getInt("authorId")));
        return bookAuthors;
    }

    public BookAuthors find(Book book, Author author) throws SQLException {
        return read(new String[] {
            "bookId",
            "authorId"
        }, new Object[] {
            book.getBookID(),
            author.getID()
        });
    }

    public List<BookAuthors> findBooksOfAuthor(Author author) throws SQLException {
        return readAll().stream()
            .filter((ba) -> ba.getAuthor().getID() == author.getID())
            .collect(Collectors.toList());
    }

    public List<BookAuthors> findAuthorsOfBook(Book book) throws SQLException {
        return readAll().stream()
            .filter((ba) -> ba.getBook().getBookID() == book.getBookID())
            .collect(Collectors.toList());
    }

    public void updateBook(BookAuthors bookAuthor, Book book) throws SQLException {
        update(new String[] {
            "bookId",
        }, new String[] {
            "bookId",
            "authorId"
        }, new Object[] {
            book.getBookID(),
            bookAuthor.getBook().getBookID(),
            bookAuthor.getAuthor().getID()
        });
    }

    public void updateAuthor(BookAuthors bookAuthor, Author author) throws SQLException {
        update(new String[] {
            "authorId",
        }, new String[] {
            "bookId",
            "authorId"
        }, new Object[] {
            author.getID(),
            bookAuthor.getBook().getBookID(),
            bookAuthor.getAuthor().getID()
        });
    }
}
