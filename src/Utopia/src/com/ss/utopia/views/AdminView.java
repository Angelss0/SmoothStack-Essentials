package com.ss.utopia.views;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.ss.utopia.util.AnsiFormat;
import com.ss.utopia.util.ConnectionsManager;
import com.ss.utopia.util.AnsiFormat.Colors;

public class AdminView extends View {

    public AdminView(Scanner scanner, View previous) {
        super(scanner, previous);
    }

    @Override
    public IOption entryPoint() {
        return optionsMenu(
            "Welcome, UMS Admin!",
            List.of(
                "Add/Update/Delete/Read Flights",
                AnsiFormat.setColor("Add/Update/Delete/Read Seats (Not implemented)", Colors.BrightBlack),
                "Add/Update/Delete/Read Bookings",
                "Add/Update/Delete/Read Airports",
                "Add/Update/Delete/Read Travelers",
                "Add/Update/Delete/Read Employees",
                AnsiFormat.setColor("Override trip cancellation for a ticket (Not implemented)", Colors.BrightBlack),
                RETURN_STR
            ), List.of(
                () -> adminSelectOperation("Flights", new AdminFlightsView(scanner, this)),
                () -> adminSelectOperation("Seats", new AdminSeatsView(scanner, this)),
                () -> adminSelectOperation("Bookings", new AdminBookingsView(scanner, this)),
                () -> adminSelectOperation("Airports", new AdminAirportView(scanner, this)),
                () -> adminSelectOperation("Travelers", new AdminTravelersView(scanner, this)),
                () -> adminSelectOperation("Employees", new AdminEmployeesView(scanner, this)),
                () -> overrideDuedate(),
                () -> previousView.entryPoint()
            )
        );
    }

    public IOption adminSelectOperation(String name, BaseAdminView modelType) {
        return optionsMenu(
            "What type of operation do you want to do?",
            List.of(
                "Add " + name,
                "Update " + name,
                "Delete " + name,
                "Read " + name,
                RETURN_STR
            ), Arrays.asList(
                () -> modelType.add(),
                () -> modelType.update(),
                () -> modelType.delete(),
                () -> modelType.read(),
                () -> entryPoint()
            )
        );
    }

    // TODO: Reimplement properly
    public IOption overrideDuedate() {
        try {
            // BookLoans bookLoan = getModelFromController(
            //     "Pick which loan to override due date:",
            //     loansController
            // );
            // if (bookLoan == null) { return entryPoint(); }

            String date = "";
            Date newDate = null;
            do {
                date = getInputPromt("Enter new due date (yyyy-[m]m-[d]d): ");
                if (date != null) {
                    try {
                        newDate = Date.valueOf(date);
                    } catch (Exception e) {
                        System.out.println("Invalid Date Format!");
                    }
                }
            } while (date.equals(""));
            if (newDate == null) { return entryPoint();}
            // loansController.updateDueDate(bookLoan, newDate);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully overrided due date!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return entryPoint();
    }
}
