package com.ss.lms.views;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminBorrowerView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminBorrowerView(Scanner scanner, AdminView adminView) {
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

            Borrower borrower = new Borrower();

            borrower.setName(name);
            borrower.setAddress(address);
            borrower.setPhone(phone);
            borrower.setCardNo(borrowerController.getMinFreeId(a -> a.getCardNo()));
            borrowerController.add(borrower);

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
            Borrower borrower = getModelFromController(
                "Select the borrower To update information of (write quit to skip):",
                borrowerController
            );
            if (borrower == null) { return returnToAdmin(); }

            String name = getInputPromt("New name (write quit to skip):");
            if (name != null) { borrowerController.updateName(borrower, name); }

            String address = getInputPromt("New address (write quit to skip):");
            if (address != null) { borrowerController.updateAddress(borrower, address); }

            String phone = getInputPromt("New Phone (write quit to skip):");
            if (phone != null) { borrowerController.updatePhone(borrower, phone); }
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Updated a borrower!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        try {
            Borrower borrower = getModelFromController(
                "Select a borrower to delete:",
                borrowerController
            );
            if (borrower == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            borrowerController.delete(borrower);
            
            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully deleted a borrower!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption read() {
        try {
            Borrower borrower = getModelFromController(
                "Select a borrower to read about:",
                borrowerController
            );
            if (borrower == null) { return returnToAdmin(); }

            System.out.println("Borrower name: " + borrower.getName()
                + "\nAddress: " + borrower.getAddress()
                + "\nPhone Number: " + borrower.getPhone()
                + "\n\nBooks Loaned:"
            );
            loansController.readAll().stream()
                .filter((b) -> b.getBorrower().getCardNo() == borrower.getCardNo())
                .forEach((b) -> System.out.println(b.toString())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Borrowers", this); }
}
