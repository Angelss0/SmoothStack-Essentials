package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Author;
import com.ss.lms.models.Book;
import com.ss.lms.models.Publisher;

public class BookController extends Controller<Book> {

    public BookController() {
        super();
        tableName = "tbl_book";
    }
    
    @Override
    public void add(Book book) throws SQLException {
        add(new Object[] {
            book.getBookID(),
            book.getTitle(),
            book.getAuthor().getID(),
            book.getPublisher().getID()
        });
    }
    
    @Override
    public void delete(Book book) throws SQLException {
        delete(new String[] {
            "bookId"
        }, new Object[] {
            book.getBookID()
        });
    }

    @Override
    protected Book extractModel(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookID(rs.getInt("bookId"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(new AuthorController().find(rs.getInt("authId")));
        book.setPublisher(new PublisherController().find(rs.getInt("pubId")));
        return book;
    }

    public Book find(int id) throws SQLException {
        return read(new String[] {
            "bookId"
        }, new Object[] {
            id
        });
    }

    public void updateId(Book book, int bookId) throws SQLException {
        update(new String[] {
            "bookId"
        }, new String[] {
            "bookId"
        }, new Object[] {
            bookId,
            book.getBookID()
        });
    }

    public void updateTitle(Book book, int bookTitle) throws SQLException {
        update(new String[] {
            "title"
        }, new String[] {
            "bookId",
        }, new Object[] {
            bookTitle,
            book.getBookID(),
        });
    }

    public void updateAuthor(Book book, Author author) throws SQLException {
        update(new String[] {
            "authId"
        }, new String[] {
            "bookId",
        }, new Object[] {
            author.getID(),
            book.getBookID(),
        });
    }

    public void updatePublisher(Book book, Publisher publisher) throws SQLException {
        update(new String[] {
            "pubId"
        }, new String[] {
            "bookId",
        }, new Object[] {
            publisher.getID(),
            book.getBookID(),
        });
    }

}
