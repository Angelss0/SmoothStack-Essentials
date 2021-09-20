package com.ss.lms.views;

import java.sql.Date;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class BorrowerView extends View {
    private MainView mainScreen;

    public BorrowerView(Scanner scanner, MainView mainScreen) {
        super(scanner);
        this.mainScreen = mainScreen;
    }
    
    public IOption borr1() {
        Borrower borrower = isValidID();
        if (borrower == null) { return null; }

        return borr1(borrower);
    }

    public IOption borr1(Borrower borrower) {
        return optionsMenu(
            "Welcome, " + borrower.getName() + "!",
            Arrays.asList(
                "Check out a book",
                "Return a book",
                "-> Return to previous"
            ), Arrays.asList(
                () -> borr1Option1(borrower),
                () -> borr1Option2(borrower),
                () -> mainScreen.mainScreen()
            )
        );
    }

    public IOption borr1Option1(Borrower borrower) {
        try {
            LibraryBranch branch = getModelFromController(
                "Pick the Branch you want to check out from:",
                branchController
            );

            if (branch == null) { return borr1(borrower); }

            BookCopies bookCopy = getModelFromController(
                "Pick the Book you want to check out:",
                copiesController,
                (copy) -> copy.getNoOfCopies() > 0
            );

            if (bookCopy == null) { return borr1(borrower); }

            BookLoans bookLoan = loansController.find(bookCopy.getBook(), branch, borrower);
            if (bookLoan != null) {
                System.out.println("You already have a copy rented out!\n");
                return borr1(borrower);
            }
            
            int copies = copiesController.removeCopies(bookCopy, 1);
            if (copies == -1) { 
                return borr1(borrower);
            }

            bookLoan = new BookLoans();
            bookLoan.setBook(bookCopy.getBook());
            bookLoan.setBorrower(borrower);
            bookLoan.setBranch(branch);
            bookLoan.setDateOut(Date.valueOf(LocalDate.now()));
            bookLoan.setDueDate(Date.valueOf(LocalDate.now().plusWeeks(1)));
            bookLoan.setDateIn(null);
            loansController.add(bookLoan);

            ConnectionsManager.getConnection().commit();

            System.out.println("Book Checked out!\n");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
            return null;
        }
        return borr1(borrower);
    }

    public IOption borr1Option2(Borrower borrower) {
        try {
            LibraryBranch branch = getModelFromController(
                "Pick the Branch you want to return to:",
                branchController
            );

            if (branch == null) { return borr1(borrower); }

            Book book = getModelFromController(
                "Pick the Book you want to return:",
                bookController
            );

            if (book == null) { return borr1(borrower); }

            BookLoans bookLoan = loansController.find(book, branch, borrower);
            if (bookLoan == null) {
                System.out.println("You don't have a copy of the book rented out!\n");
                return borr1(borrower);
            }
            
            BookCopies bookCopy = copiesController.find(book, branch);
            copiesController.addCopies(bookCopy, 1);
            
            loansController.delete(bookLoan);

            ConnectionsManager.getConnection().commit();

            System.out.println("Book Returned!\n");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
            return null;
        }
        return borr1(borrower);
    }

    private Borrower isValidID() {
        int idCard = -1;

        while(idCard == -1) {
            try {
                String input = getInputPromt("Enter the your Card Number: ");

                if (input == null) { return null; }

                idCard = Integer.parseInt(input);
                if (idCard > -1) {
                    Borrower borrower = borrowerController.find(idCard);
                    if (borrower == null) {
                        System.out.println("ID not found!");
                        idCard = -1;
                    } else {
                        return borrower;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                idCard = -1;
            } catch (NumberFormatException e) {
                idCard = -1;
            }
        }

        return null;
    }
}
