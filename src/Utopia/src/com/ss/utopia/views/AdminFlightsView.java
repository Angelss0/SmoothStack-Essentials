package com.ss.utopia.views;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.ss.utopia.controllers.*;
import com.ss.utopia.models.*;

import com.ss.utopia.util.ConnectionsManager;

public class AdminFlightsView extends BaseAdminView {

    public AdminFlightsView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption add() {
        try {
            AirportController airportController = new AirportController();

            Airport origin = getModelFromController("Origin/Departure Airports", airportController);
            if (origin == null) { return previousView.entryPoint(); }

            Airport destination = getModelFromController("Arrival/Destination Airports", airportController);
            if (destination == null) { return previousView.entryPoint(); }

            String date = getInputPromt("Type out a date for a new flight (yyyy-[m]m-[d]d) (type quit to return):");
            if (date == null) { return previousView.entryPoint(); }

            String time = getInputPromt("Type out a time for a new flight (hh:mm:ss[.f...]) (type quit to return):");
            if (time == null) { return previousView.entryPoint(); }

            Timestamp departureTime = Timestamp.valueOf(date + " " + time);

            Flight flight = new Flight();

            RouteController routeController = new RouteController();
            Route route = routeController.findByAirports(origin, destination);
            if (route == null) {
                route = new Route(routeController.getMinFreeId((r) -> r.getId()), origin, destination);
                routeController.add(route);
            }

            int maximumSeats = -1;
            do {
                try {
                    maximumSeats = Integer.parseInt(getInputPromt("How many seats?"));
                } catch (Exception e) {
                    maximumSeats = -1;
                }
            } while (maximumSeats == -1);

            final int currentSeats = maximumSeats;
            Airplane airplane = getModelFromController(
                "Select model with " + maximumSeats + " or more:",
                new AirplaneController(),
                (a) -> a.getAirplaneType().getMaxCapacity() >= currentSeats
            );
            if (airplane == null) {
                System.out.println("Making a new airplane!");

                airplane = new Airplane();
                AirplaneController airplaneController = new AirplaneController();
                AirplaneTypeController airplaneTypeController = new AirplaneTypeController();

                AirplaneType airplaneType = getModelFromController(
                    "Select Airplane type with seat amount:",
                    airplaneTypeController,
                    (at) -> at.getMaxCapacity() == currentSeats
                );

                if (airplaneType == null) {
                    airplaneType = new AirplaneType();
                    airplaneType.setId(airplaneTypeController.getMinFreeId((at) -> at.getId()));
                    airplaneType.setMaxCapacity(maximumSeats);
                    airplaneTypeController.add(airplaneType);
                }

                airplane.setId(airplaneController.getMinFreeId((a) -> a.getId()));
                airplane.setAirplaneType(airplaneType);
                airplaneController.add(airplane);
            }

            flight.setId(new FlightController().getMinFreeId((f) -> f.getId()));
            flight.setRoute(route);
            flight.setReservedSeats(0);
            flight.setSeatPrice(0);
            flight.setDepartureTime(departureTime);
            flight.setAirplane(airplane);

            new FlightController().add(flight);

            ConnectionsManager.getConnection().commit();
            System.out.println("Added a new Flight!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return previousView.entryPoint();
    }

    @Override
    public IOption update() {
        try {
            var flightController = new FlightController();
            Flight flight = getModelFromController("Choose a flight to update: ", flightController);
            if (flight == null) { return previousView.entryPoint(); }

            var routeController = new RouteController();
            Route route = getModelFromController("Choose a new route or create a new one: ", routeController);
            if (route != null) {
                flightController.updateRoute(flight, route);
            } else {
                var airportController = new AirportController();
                Airport origin = getModelFromController("Origin/Departure Airports", airportController);
                Airport destination = getModelFromController("Arrival/Destination Airports", airportController);
                if (origin != null && destination != null) {
                    route = new Route(routeController.getMinFreeId((r) -> r.getId()), origin, destination);
                    routeController.add(route);
                    flightController.updateRoute(flight, route);
                }
            }

            String currentDepartureTime = flight.getDepartureTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
            String date = getInputPromt("Type out a date for a new flight (yyyy-[m]m-[d]d) (type quit to return):");
            if (date != null) {
                Timestamp departureTime = Timestamp.valueOf(date + " " + currentDepartureTime);
                flightController.updateDepartureTime(flight, departureTime);
                flight.setDepartureTime(departureTime);
            }

            String currentDepartureDate = flight.getDepartureTime().toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE);
            String time = getInputPromt("Type out a time for a new flight (hh:mm:ss[.f...]) (type quit to return):");
            if (time != null) {
                Timestamp departureTime = Timestamp.valueOf(currentDepartureDate + " " + time);
                flightController.updateDepartureTime(flight, departureTime);
                flight.setDepartureTime(departureTime);
            }

            Float seatPrice = getFloatPromt("Update seat price: ");
            if (seatPrice != null) { flightController.updateSeatPrice(flight, seatPrice); }

            ConnectionsManager.getConnection().commit();
            System.out.println("Updated flight details!");
        } catch (SQLException e) {
            ConnectionsManager.rollbackConnection();
        }
        return previousView.entryPoint();
    }

    @Override
    public IOption delete() {
        return deleteModel("flights", new FlightController());
    }

    @Override
    public IOption read() {
        return readModel("flights", new FlightController());
    }

    @Override
    public IOption returnToAdmin() { return previousView.entryPoint(); }

    @Override
    public IOption entryPoint() { return null; }

}
