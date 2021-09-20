package com.ss.lms.views;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminPublisherView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminPublisherView(Scanner scanner, AdminView adminView) {
        super(scanner);
        this.adminView = adminView;
    }

    @Override
    public IOption add() {
        try {
            String name = getInputPromt("Name: ");
            if (name == null) { return returnToAdmin(); }

            String address = getInputPromt("Address: ");
            if (address == null) { return returnToAdmin(); }

            String phone = getInputPromt("Phone: ");
            if (phone == null) { return returnToAdmin(); }

            Publisher publisher = new Publisher();

            publisher.setName(name);
            publisher.setAddress(address);
            publisher.setPhone(phone);
            publisher.setID(publisherController.getMinFreeId(a -> a.getID()));
            publisherController.add(publisher);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully added a publisher!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption update() {
        try {
            Publisher publisher = getModelFromController(
                "Select the publisher To update information of (write quit to skip):",
                publisherController
            );
            if (publisher == null) { return returnToAdmin(); }

            String name = getInputPromt("New name (write quit to skip):");
            if (name != null) { publisherController.updateName(publisher, name); }

            String address = getInputPromt("New address (write quit to skip):");
            if (address != null) { publisherController.updateAddress(publisher, address); }

            String phone = getInputPromt("New Phone (write quit to skip):");
            if (phone != null) { publisherController.updatePhone(publisher, phone); }
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Updated a publisher!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        try {
            Publisher publisher = getModelFromController(
                "Select publisher to delete:",
                publisherController
            );
            if (publisher == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            publisherController.delete(publisher);
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully deleted a publisher!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        try {
            Publisher publisher = getModelFromController(
                "Select Publisher To read about:",
                publisherController
            );
            if (publisher == null) { return returnToAdmin(); }

            System.out.println("Publisher name: " + publisher.getName()
                + "\nAddress: " + publisher.getAddress()
                + "\nPhone Number: " + publisher.getPhone()
                + "\n\nBooks published:"
            );
            bookController.readAll().stream()
                .filter((b) -> b.getPublisher().getID() == publisher.getID())
                .forEach((b) -> System.out.println(b.getMenuRep())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Publishers", this); }
}
