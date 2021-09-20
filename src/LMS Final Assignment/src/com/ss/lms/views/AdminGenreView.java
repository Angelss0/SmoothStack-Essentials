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
        try {
            String name = getInputPromt("Name: ");
            if (name == null) { return returnToAdmin(); }

            Genre genre = new Genre();

            genre.setName(name);
            genre.setID(genreController.getMinFreeId(a -> a.getID()));
            genreController.add(genre);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully added a borrower!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption update() {
        try {
            Genre genre = getModelFromController(
                "Select the genre to update the name of (write quit to skip):",
                genreController
            );
            if (genre == null) { return returnToAdmin(); }

            String name = getInputPromt("New name (write quit to skip):");
            if (name != null) { genreController.updateName(genre, name); }

            ConnectionsManager.getConnection().commit();
            System.out.println("Updated a genre!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        try {
            Genre genre = getModelFromController(
                "Select genre to delete:",
                genreController
            );
            if (genre == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            genreController.delete(genre);
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully deleted a genre!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        try {
            Genre genre = getModelFromController(
                "Select a genre to read about:",
                genreController
            );
            if (genre == null) { return returnToAdmin(); }

            System.out.println("Genre: " + genre.getName()
                + "\n\nBooks with this genre:"
            );
            bookGenreController.readAll().stream()
                .filter((b) -> b.getGenre().getID() == genre.getID())
                .forEach((b) -> System.out.println(b.getBook().getMenuRep())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Genres", this); }
}
