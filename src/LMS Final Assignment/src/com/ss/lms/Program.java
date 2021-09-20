package com.ss.lms;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.ss.lms.util.ConnectionsManager;
import com.ss.lms.views.*;

public class Program {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        MainView mainView = new MainView(scanner);
        IOption currentOption = mainView.mainScreen();

        try {
            while (currentOption != null) {
                currentOption = currentOption.invoke();
            }
        } catch (InputMismatchException e) {}

        ConnectionsManager.closeConnection();
        scanner.close();
    }
}
