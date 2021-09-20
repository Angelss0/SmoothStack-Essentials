package com.ss.lms.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

import com.ss.lms.models.*;

public class AdminView extends View {
    private MainView mainScreen;
    private AdminBooksView booksView = new AdminBooksView(scanner, this);
    private AdminAuthorView authorView = new AdminAuthorView(scanner, this);
    private AdminBorrowerView borrowerView = new AdminBorrowerView(scanner, this);
    private AdminGenreView genreView = new AdminGenreView(scanner, this);
    private AdminLibraryBranchView branchView = new AdminLibraryBranchView(scanner, this);
    private AdminPublisherView publisherView = new AdminPublisherView(scanner, this);

    public AdminView(Scanner scanner, MainView mainScreen) {
        super(scanner);
        this.mainScreen = mainScreen;
    }

    public IOption admin1() {
        List<Book> books = new ArrayList<>();
        
        try {
            books = bookController.readAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        var bookRep = new StringJoiner("\n");
        books.forEach((book) -> bookRep.add(book.toString()));

        return optionsMenu(
            "Welcome, LMS Admin!\n\nBooks in library:\n" + bookRep.toString(),
            List.of(
                "Add/Update/Delete/Read Books",
                "Add/Update/Delete/Read Authors",
                "Add/Update/Delete/Read Genres",
                "Add/Update/Delete/Read Publishers",
                "Add/Update/Delete/Read Library Branches",
                "Add/Update/Delete/Read Borrowers",
                "Override Due Date for a Book Loan",
                RETURN_STR
            ), List.of(
                () -> adminSelectOperation("Books", booksView),
                () -> adminSelectOperation("Authors", authorView),
                () -> adminSelectOperation("Genres", genreView),
                () -> adminSelectOperation("Publishers", publisherView),
                () -> adminSelectOperation("Library Branches", branchView),
                () -> adminSelectOperation("Borrowers", borrowerView),
                () -> promter("Overriding a books due date!"),
                () -> mainScreen.mainScreen()
            )
        );
    }

    public IOption adminSelectOperation(String name, IAdminModel modelType) {
        return optionsMenu(
            "What type of operation do you want to do?",
            List.of(
                "Add " + name,
                "Update " + name,
                "Delete " + name,
                "Read " + name,
                RETURN_STR
            ), Arrays.asList(
                () -> modelType.add(),
                () -> modelType.update(),
                () -> modelType.delete(),
                () -> modelType.read(),
                () -> admin1()
            )
        );
    }
}
