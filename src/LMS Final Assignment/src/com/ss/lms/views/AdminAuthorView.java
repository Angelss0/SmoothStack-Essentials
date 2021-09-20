package com.ss.lms.views;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminAuthorView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminAuthorView(Scanner scanner, AdminView adminView) {
        super(scanner);
        this.adminView = adminView;
    }

    @Override
    public IOption add() {
        try {
            String name = getInputPromt("Name: ");
            if (name == null) { return returnToAdmin(); }
            Author author = new Author();

            author.setName(name);
            author.setID(authorController.getMinFreeId(a -> a.getID()));
            authorController.add(author);

            ConnectionsManager.getConnection().commit();
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption update() {
        try {
            Author author = getModelFromController(
                "Select Author To change the name of (write quit to skip):",
                authorController
            );
            if (author == null) { return returnToAdmin(); }

            String name = getInputPromt("New name (write quit to skip):");
            if (name != null) { authorController.updateName(author, name); }
            
            ConnectionsManager.getConnection().commit();
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        try {
            Author author = getModelFromController(
                "Select Author to delete:",
                authorController
            );
            if (author == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            authorController.delete(author);
            
            ConnectionsManager.getConnection().commit();

            System.out.println("Successfully deleted the Author!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        try {
            Author author = getModelFromController(
                "Select Author To read about:",
                authorController
            );
            if (author == null) { return returnToAdmin(); }

            System.out.println("Author name:\n" + author.getName() + "\n\nBooks Authored:");
            bookAuthorsController.findBooksOfAuthor(author)
                .forEach((ba) -> System.out.println(ba.getBook().getTitle())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Authors", this); }
}
