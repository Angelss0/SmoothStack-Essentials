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
            
            var authors = selectMultipleAuthors(book);
            if (authors.size() > 0) {
                book.setAuthors(authors);
                book.setAuthor(authors.get(0).getAuthor());
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

            book.setTitle(title);
            book.setBookID(bookController.getMinFreeId((b) -> b.getBookID()));
            book.setPublisher(publisher);
            bookController.add(book);
            
            bookGenreController.refreshFromList(genres, (genre) -> genre.getBook().getBookID() == book.getBookID());
            bookAuthorsController.refreshFromList(authors, (author) -> author.getBook().getBookID() == book.getBookID());

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
        try {
            Book book = getModelFromController(
                "Select Book to update: ",
                bookController
            );

            String title = getInputPromt("new title (write quit to skip): ");
            if (title != null) { bookController.updateTitle(book, title); }

            var authors = selectMultipleAuthors(book);
            if (authors.size() > 0) {
                book.setAuthors(authors);
                book.setAuthor(authors.get(0).getAuthor());
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
            
            bookGenreController.refreshFromList(genres, (genre) -> genre.getBook().getBookID() == book.getBookID());
            bookAuthorsController.refreshFromList(authors, (author) -> author.getBook().getBookID() == book.getBookID());

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully added a new book to the library!");

        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        try {
            Book book = getModelFromController(
                "Select book to delete:",
                bookController
            );
            if (book == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            bookController.delete(book);
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully deleted a book!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        try {
            Book book = getModelFromController(
                "Select book To read about:",
                bookController
            );
            if (book == null) { return returnToAdmin(); }
            System.out.println(book.toString()
                + "\nAuthors:"
            );
            bookAuthorsController.readAll().stream()
                .filter((b) -> b.getBook().getBookID() == book.getBookID())
                .forEach((b) -> System.out.println(b.getAuthor().getMenuRep())
            );
            System.out.println("\nGenres:");
            bookGenreController.readAll().stream()
                .filter((b) -> b.getBook().getBookID() == book.getBookID())
                .forEach((b) -> System.out.println(b.getGenre().getMenuRep())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
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
