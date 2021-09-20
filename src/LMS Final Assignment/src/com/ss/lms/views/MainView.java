package com.ss.lms.views;

import java.util.Arrays;
import java.util.Scanner;

public class MainView extends View {
    public MainView(Scanner scanner) {
        super(scanner);
    }

    LibrarianView librarianView = new LibrarianView(scanner, this);
    BorrowerView borrowerView = new BorrowerView(scanner, this);
    AdminView adminView = new AdminView(scanner, this);

    public IOption mainScreen() {
        return optionsMenu(
            "Welcome to the SS Library Management System. Which category of user are you?"
            , Arrays.asList(
                "Librarian",
                "Administrator",
                "Borrower",
                QUIT_STR
            ), Arrays.asList(
                () -> librarianView.lib1(),
                () -> adminView.admin1(),
                () -> borrowerView.borr1(),
                null
            )
        );
    }
}
