package com.ss.utopia;

import java.util.Scanner;

import com.ss.utopia.util.ConnectionsManager;
import com.ss.utopia.views.*;

public class Program {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        View mainView = new MainView(scanner, null);
        IOption currentOption = mainView.entryPoint();

        ConnectionsManager.setDatabase("utopia");

        try {
            while (currentOption != null) { currentOption = currentOption.invoke(); }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ConnectionsManager.closeConnection();
        scanner.close();
    }
}
