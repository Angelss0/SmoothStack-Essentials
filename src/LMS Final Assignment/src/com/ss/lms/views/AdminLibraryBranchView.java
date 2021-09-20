package com.ss.lms.views;

import java.sql.SQLException;
import java.util.Scanner;

import com.ss.lms.models.*;
import com.ss.lms.util.ConnectionsManager;

public class AdminLibraryBranchView extends View implements IAdminModel {
    private AdminView adminView;

    public AdminLibraryBranchView(Scanner scanner, AdminView adminView) {
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

            LibraryBranch branch = new LibraryBranch();

            branch.setName(name);
            branch.setAddress(address);
            branch.setID(branchController.getMinFreeId(a -> a.getID()));
            branchController.add(branch);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully added a branch!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    @Override
    public IOption update() {
        try {
            LibraryBranch branch = getModelFromController(
                "Select the library branch to update (write quit to skip):",
                branchController
            );
            if (branch == null) { return returnToAdmin(); }

            String name = getInputPromt("New name (write quit to skip):");
            if (name != null) { branchController.updateName(branch, name); }

            String address = getInputPromt("New address (write quit to skip):");
            if (address != null) { branchController.updateAddress(branch, address); }

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
            LibraryBranch branch = getModelFromController(
                "Select genre to delete:",
                branchController
            );
            if (branch == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            branchController.delete(branch);
            
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
            LibraryBranch branch = getModelFromController(
                "Select a Library Branch to read about:",
                branchController
            );
            if (branch == null) { return returnToAdmin(); }

            System.out.println(branch.getMenuRep()
                + "\n\nBooks in this library:"
            );
            copiesController.readAll().stream()
                .filter((b) -> b.getBranch().getID() == branch.getID()
                    && b.getNoOfCopies() > 0)
                .forEach((b) -> System.out.println(b.getMenuRep())
            );
            System.out.println("\nBooks loaned out of this library:");
            loansController.readAll().stream()
                .filter((b) -> b.getBranch().getID() == branch.getID())
                .forEach((b) -> System.out.println(b.toString())
            );
            System.out.println();
        } catch (Exception e) {
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption returnToAdmin() { return adminView.adminSelectOperation("Branches", this); }
}
