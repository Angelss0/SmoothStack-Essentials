package com.ss.lms.views;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminGenreView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminGenreView(Scanner scanner, AdminView adminView) {
        super(scanner);
        this.adminView = adminView;
    }

    @Override
    public IOption add() {
        // TODO Auto-generated method stub
        return returnToAdmin();
    }

    @Override
    public IOption update() {
        // TODO Auto-generated method stub
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        // TODO Auto-generated method stub
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        // TODO Auto-generated method stub
        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Authors", this); }
}
