package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.ss.utopia.controllers.AirportController;
import com.ss.utopia.models.Airport;

public class AdminAirportView extends BaseAdminView {

    public AdminAirportView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption add() {
        return addModel(
            "airport",
            new String[] {
                "IATA id",
                "City"
            }, (args) -> {
                Airport airport = new Airport();
                airport.setIataID(args[0]);
                airport.setCityName(args[1]);
                return airport;
            },
            new AirportController()
        );
    }

    @Override
    public IOption update() {
        var controller = new AirportController();

        return updateModel("employee", new String[] {
            "City"
        }, Arrays.asList(
            (u, arg) -> { try { controller.updateCity(u, arg); } catch (SQLException e) {} }
        ),  controller
        );
    }

    @Override
    public IOption delete() {
        return deleteModel("airport", new AirportController());
    }

    @Override
    public IOption read() {
        return readModel("airport", new AirportController());
    }

    @Override
    public IOption returnToAdmin() { return previousView.entryPoint(); }

    @Override
    public IOption entryPoint() { return null; }

}
