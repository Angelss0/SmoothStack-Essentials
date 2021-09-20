package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Book;
import com.ss.lms.models.BookGenres;
import com.ss.lms.models.Genre;

public class BookGenresController extends Controller<BookGenres> {

    public BookGenresController() {
        super();
        tableName = "tbl_book_genres";
    }

    @Override
    public void add(BookGenres bookGenres) throws SQLException {
        add(new Object[] {
            bookGenres.getGenre().getID(),
            bookGenres.getBook().getBookID()
        });
    }

    @Override
    public void delete(BookGenres bookGenres) throws SQLException {
        delete(new String[] {
            "genre_id",
            "bookId"
        }, new Object[] {
            bookGenres.getBook().getBookID(),
            bookGenres.getGenre().getID()
        });
    }

    @Override
    protected BookGenres extractModel(ResultSet rs) throws SQLException {
        GenreController genreController = new GenreController();
        BookController bookController = new BookController();

        BookGenres bookGenres = new BookGenres();
        bookGenres.setGenre(genreController.find(rs.getInt("genre_id")));
        bookGenres.setBook(bookController.find(rs.getInt("bookId")));
        return bookGenres;
    }

    public BookGenres find(Book book, Genre genre) throws SQLException {
        return read(new String[] {
            "genre_id",
            "bookId"
        }, new Object[] {
            book.getBookID(),
            genre.getID()
        });
    }

    public void updateGenre(BookGenres bookGenre, Genre genre) throws SQLException {
        update(new String[] {
            "genre_id"
        }, new String[] {
            "genre_id",
            "bookId"
        }, new Object[] {
            genre.getID(),
            bookGenre.getGenre().getID(),
            bookGenre.getBook().getBookID()
        });
    }

    public void updateBook(BookGenres bookGenre, Book book) throws SQLException {
        update(new String[] {
            "bookId"
        }, new String[] {
            "genre_id",
            "bookId"
        }, new Object[] {
            book.getBookID(),
            bookGenre.getGenre().getID(),
            bookGenre.getBook().getBookID()
        });
    }
    
}
