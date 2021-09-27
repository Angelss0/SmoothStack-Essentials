package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.ss.utopia.controllers.FlightBookingController;
import com.ss.utopia.controllers.FlightController;
import com.ss.utopia.controllers.UserController;
import com.ss.utopia.models.Flight;
import com.ss.utopia.models.FlightBookings;
import com.ss.utopia.models.User;
import com.ss.utopia.util.AnsiFormat;
import com.ss.utopia.util.AnsiFormat.Colors;

public class TravelerView extends View {

    public TravelerView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption entryPoint() {
        int membershipNum = -1;
        do {
            String input = getInputPromt("Enter your membership number: ");
            if (input == null) { return null; }

            try {
                membershipNum = Integer.parseInt(input);
                User user = new UserController().find(membershipNum);

                if (user != null && user.getRole().getId() == 1) { return trav1(user); }
                else if (user != null && user.getRole().getId() == 0) {
                    System.out.println(AnsiFormat.setColor("\nMembership id isn't a traveler! It's an agent!", Colors.Yellow));
                    membershipNum = -1;
                }
                else {
                    System.out.println(AnsiFormat.setColor("\nMembership id not found!", Colors.Red));
                    membershipNum = -1;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                membershipNum = -1;
            } catch (SQLException e) {
                e.printStackTrace();
                membershipNum = -1;
            }

        } while(membershipNum == -1);

        return null;
    }

    public IOption trav1(User user) {
        return optionsMenu(
            "Welcome, " + user.getUsername()
            , Arrays.asList(
                "Book a ticket."
                , "Cancel an upcoming trip."
                , RETURN_STR
            ), Arrays.asList(
                () -> trav1Option1(user),
                () -> trav1Option2(user),
                () -> previousView.entryPoint()
            ));
    }

    public IOption trav1Option1(User user) {
        try {
            Flight flight = getModelFromController(
                "Pick the flight you want to book a ticket for: ",
                new FlightController()
            );

            return trav1Option1_1(user, flight);
        } catch (SQLException e) { }

        return null;
    }

    // TODO: implement proper First, Bussiness, and Economy booking.
    public IOption trav1Option1_1(User user, Flight flight) {
        return optionsMenu(
            "Pick the Seat you want to book a ticket for: ",
            Arrays.asList(
                "View flight details",
                "First",
                "Bussiness",
                "Economy",
                RETURN_STR
            ), Arrays.asList(
                () -> {
                    System.out.println(flight.getReadView());
                    return () -> trav1Option1_1(user, flight);
                },
                () -> promter("Picked First-Class!"),
                () -> promter("Picked Bussiness-Class!"),
                () -> promter("Picked Economy-Class!"),
                () -> trav1(user)
            ));
    }

    // TODO: Proper implementation
    public IOption trav1Option2(User user) {
        try {
            Flight flightBooking = getModelFromController(
                "Pick the flight booking you want to cancel: ",
                new FlightController()
            );

            // return trav1Option1_1(user, flight);
        } catch (SQLException e) { }

        return null;
    }
}
