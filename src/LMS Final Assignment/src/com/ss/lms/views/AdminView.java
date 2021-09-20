package com.ss.lms.views;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

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
        return optionsMenu(
            "Welcome, LMS Admin!",
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
                () -> overrideDuedate(),
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

    public IOption overrideDuedate() {
        try {
            BookLoans bookLoan = getModelFromController(
                "Pick which loan to override due date:",
                loansController
            );
            if (bookLoan == null) { return admin1(); }

            String date = "";
            Date newDate = null;
            do {
                date = getInputPromt("Enter new due date (yyyy-[m]m-[d]d): ");
                if (date != null) {
                    try {
                        newDate = Date.valueOf(date);
                    } catch (Exception e) {
                        System.out.println("Invalid Date Format!");
                    }
                }
            } while (date.equals(""));
            if (newDate == null) { return admin1();}
            loansController.updateDueDate(bookLoan, newDate);
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully overrided due date!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return admin1();
    }
}
