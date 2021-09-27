package com.ss.utopia.views;

import java.util.Scanner;

public class AdminSeatsView extends BaseAdminView {

    // TODO: Implement flight_seats table before continuing.

    public AdminSeatsView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption add() {
        // TODO Implement Add method!
        return null;
    }

    @Override
    public IOption update() {
        // TODO Implement Update method!
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

    @Override
    public IOption returnToAdmin() { return previousView.entryPoint(); }

    @Override
    public IOption entryPoint() { return null; }

}
