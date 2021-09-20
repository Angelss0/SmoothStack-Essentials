package com.ss.lms.views;

import java.util.Scanner;
import java.sql.SQLException;
import java.util.Arrays;

import com.ss.lms.models.*;

import com.ss.lms.util.AnsiFormat;
import com.ss.lms.util.ConnectionsManager;

public class LibrarianView extends View {
    private MainView mainScreen;

    public LibrarianView(Scanner scanner, MainView mainScreen) {
        super(scanner);
        this.mainScreen = mainScreen;
    }
    
    public IOption lib1() {
        return optionsMenu(
            "Welcome Librarian!",
            Arrays.asList(
                "Enter Branch you manage",
                RETURN_STR
            ), Arrays.asList(
                () -> lib2(),
                () -> mainScreen.mainScreen()
            )
        );
    }

    public IOption lib2() {
        try {
            LibraryBranch branch = getModelFromController(
                "Branches Found:",
                branchController
            );

            return branch != null ? lib3(branch) : lib1();
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
            return null;
        }
    }

    public IOption lib3(LibraryBranch branch) {
        return optionsMenu(
            "Welcome, Librarian of " + branch.getName() + "!",
            Arrays.asList(
                "Update the details of the Library",
                "Add copies of Book to the Branch",
                RETURN_STR
            ), Arrays.asList(
                () -> lib3Option1(branch),
                () -> lib3Option2(branch),
                () -> lib2()
            )
        );
    }

    public IOption lib3Option1(LibraryBranch branch) {
        System.out.println("You have chosen to update the Branch with " +
        "Branch Id:" + branch.getID() + " and Branch Name: " + branch.getName() +
        ".\n" + AnsiFormat.BOLD_BEGIN + "Enter ‘quit’ at any prompt to cancel operation." + AnsiFormat.BOLD_END + "\n");

        String branchName = getInputPromt("Please enter new branch name or enter N/A for no change: ");
        if (branchName == null) { return lib3(branch); }
        else if (branchName.equals("N/A") || branchName.equals("n/a")) {
            branchName = null;
        }

        String branchAddress = getInputPromt("Please enter new branch address or enter N/A for no change: ");
        if (branchAddress == null) { return lib3(branch); }
        else if (branchAddress.equals("N/A") || branchAddress.equals("n/a")) {
            branchAddress = null;
        }

        try {
            if (branchName != null) { branchController.updateName(branch, branchName); }
            if (branchAddress != null) { branchController.updateAddress(branch, branchAddress); }
            branch = branchController.find(branch.getID());
            ConnectionsManager.getConnection().commit();
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
            return null;
        }

        System.out.println("\nBranch was successfully updated!\n");
        return lib3(branch);
    }

    public IOption lib3Option2(LibraryBranch branch) {
        try {
            Book chosenBook = getModelFromController(
                "Pick the Book you want to add copies of, to your branch:",
                bookController
            );

            if (chosenBook == null) { return lib3(branch); }
    
            BookCopies copies = getValidCopies(branch, chosenBook);
            return updateBranchCopies(branch, copies);

        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
            return null;
        }
    }

    private BookCopies getValidCopies(LibraryBranch branch, Book chosenBook) throws SQLException {
        int existingCopies = 0;

        BookCopies copies = copiesController.find(chosenBook, branch);
        if (copies != null) {
            existingCopies = copies.getNoOfCopies();
        } else {
            copies = new BookCopies();
            copies.setBook(chosenBook);
            copies.setBranch(branch);
            copies.setNoOfCopies(0);
            copiesController.add(copies);
        }
        System.out.println("Existing number of copies: " + existingCopies + "\n");
        return copies;
    }

    private IOption updateBranchCopies(LibraryBranch branch, BookCopies copies) throws SQLException {
        int bookCopies = -1;
        while (bookCopies == -1) {
            try {
                String input = getInputPromt("Enter new number of copies:");
                if (input == null) { return lib3(branch); }
                bookCopies = Integer.parseInt(input);
            } catch (Exception e) {
                bookCopies = -1;
            }
        }

        copies.setNoOfCopies(bookCopies);
        copiesController.updateNoOfCopies(copies, bookCopies);
        ConnectionsManager.getConnection().commit();

        return lib3(branch);
    }
}
