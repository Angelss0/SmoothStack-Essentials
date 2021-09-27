package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.ss.utopia.controllers.FlightController;
import com.ss.utopia.models.Flight;
import com.ss.utopia.util.ConnectionsManager;

public class AgentView extends View {

    public AgentView(Scanner scanner, View previous) {
        super(scanner, previous);
    }

    @Override
    public IOption entryPoint() {
        return optionsMenu(
            "Welcome Utopia Agent/Employee!",
            Arrays.asList(new String[] {
                "Enter flights you manage.",
                RETURN_STR
            }),
            Arrays.asList(new IOption[] {
                () -> agent2(),
                () -> previousView.entryPoint()
            }));
    }

    public IOption agent2() {
        try {
            Flight flight = getModelFromController(
                "Flights in session:"
                , new FlightController()
            );

            if (flight == null) { return entryPoint(); }
            return agent3(flight);
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return entryPoint();
    }

    public IOption agent3(Flight flight) {
        return optionsMenu(
            "Selected the " + flight.getReadView() + " flight!"
            , Arrays.asList(
                "View more details about the flight."
                , "Update the details of the flight."
                , "Add Seats to flight"
                , RETURN_STR
            ), Arrays.asList(
                () -> promter("Viewing more details!"),
                () -> promter("Updating more details!"),
                () -> promter("Adding more seats!"),
                () -> agent2()
            )
        );
    }

    // TODO: Implement all the options!

    public IOption agent3Option1(Flight flight) {
        return null;
    }

    public IOption agent3Option2(Flight flight) {
        return null;
    }

    public IOption agent3Option3(Flight flight) {
        return null;
    }
}
