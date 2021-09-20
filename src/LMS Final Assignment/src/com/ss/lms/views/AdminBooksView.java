package com.ss.lms.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminBooksView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminBooksView(Scanner scanner, AdminView adminView) {
        super(scanner);
        this.adminView = adminView;
    }

    @Override
    public IOption add() {
        try {
            Book book = new Book();

            String title = getInputPromt("Title: ");
            if (title == null) { return returnToAdmin(); }

            book.setTitle(title);
            book.setBookID(bookController.getMinFreeId((b) -> b.getBookID()));
            
            var authors = selectMultipleAuthors(book);
            if (authors.size() > 0) {
                book.setAuthors(authors);
                book.setAuthor(book.getAuthors().get(0).getAuthor());
            }

            var genres = selectMultipleGenres(book);
            if (genres.size() > 0) {
                book.setGenres(genres);
            }

            Publisher publisher = getModelFromController(
                "Choose a Publisher: "
                , publisherController
            );

            if (publisher == null) { return returnToAdmin(); }
            book.setPublisher(publisher);

            bookController.add(book);
            authors.forEach(a -> {
                try {
                    bookAuthorsController.add(a);
                } catch (SQLException e) {
                    ConnectionsManager.rollbackConnection();
                }
            });
            genres.forEach(g -> {
                try {
                    bookGenreController.add(g);
                } catch (SQLException e) {
                    ConnectionsManager.rollbackConnection();
                }
            });

            ConnectionsManager.getConnection().commit();

            System.out.println("Successfully added a new book to the library!");

        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption update() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IOption delete() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IOption read() {
        // TODO Auto-generated method stub
        return null;
    }

    private List<BookGenres> selectMultipleGenres(Book book) throws SQLException {
        var genresList = new ArrayList<BookGenres>();
        Genre genre = null;
        do {
            genre = getModelFromController(
                "Genres in library:"
                , genreController
            );

            if (genre != null) {
                BookGenres bookGenre = new BookGenres();
                bookGenre.setGenre(genre);
                bookGenre.setBook(book);

                genresList.add(bookGenre);
            }
        } while(genre != null);
        return genresList;
    }

    private List<BookAuthors> selectMultipleAuthors(Book book) throws SQLException {
        var authorsList = new ArrayList<BookAuthors>();
        Author author = null;
        do {
            author = getModelFromController(
                "\nChoose an Author:"
                , authorController
                , (a) -> !authorsList.stream()
                    .map((ba) -> ba.getAuthor())
                    .collect(Collectors.toList())
                    .contains(a)
            );

            if (author != null) {
                BookAuthors bookAuthor = new BookAuthors();
                bookAuthor.setAuthor(author);
                bookAuthor.setBook(book);

                authorsList.add(bookAuthor);
            }
        } while(author != null);
        return authorsList;
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Books", this); }
}
