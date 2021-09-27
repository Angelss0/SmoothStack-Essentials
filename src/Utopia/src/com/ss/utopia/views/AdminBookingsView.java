package com.ss.utopia.views;

import java.sql.Date;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.ss.utopia.controllers.*;
import com.ss.utopia.models.*;
import com.ss.utopia.util.AnsiFormat;
import com.ss.utopia.util.ConnectionsManager;
import com.ss.utopia.util.AnsiFormat.Colors;

public class AdminBookingsView extends BaseAdminView {

    public AdminBookingsView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption add() {
        try {
            Flight flight = getModelFromController("Pick a flight to add tickets to: ", new FlightController());
            if (flight == null) { return returnToAdmin(); }

            System.out.println("Reserved Seats: " + flight.getReservedSeats() + "/" + flight.getAirplane().getAirplaneType().getMaxCapacity());
            int availableSeats = flight.getAirplane().getAirplaneType().getMaxCapacity() - flight.getReservedSeats();
            int reservedSeats = flight.getReservedSeats();

            User agent = getModelFromController(
                "Choose employee to be the booking agent: ",
                new UserController(),
                (u) -> u.getRole().getId() == User.EMPLOYEE
            );
            if (agent == null) { return returnToAdmin(); }

            User user = getModelFromController(
                "Choose the user to be the booking user: ",
                new UserController(),
                (u) -> u.getRole().getId() == User.TRAVELER
            );
            if (user == null) { return returnToAdmin(); }

            Integer passegerCount = null;
            do {
                passegerCount = getIntPromt("Get passenger Count: ");
                if (passegerCount == null) { return returnToAdmin(); }
                else if (passegerCount < 1) { System.out.println(AnsiFormat.setColor("This booking needs at least 1 passenger!", Colors.Yellow)); }
                else if (passegerCount > availableSeats) { System.out.println(AnsiFormat.setColor("Not enought seats for the amount of passengers!", Colors.Yellow)); }
            } while (passegerCount < 1 || passegerCount > availableSeats);

            System.out.println("\nCreate a passenger:");

            List<Passenger> passengers = new ArrayList<>();
            for (int i = 0; i < passegerCount; i++) {
                System.out.println("\nPassenger " + i + ":");
                Passenger passenger = createAPassenger();
                if (passenger == null) { return returnToAdmin(); }

                passengers.add(passenger);
            }
            System.out.println("Added all passengers to the booking.");

            String stripeId = getInputPromt("Stripe Id: ");
            if (stripeId == null) { return returnToAdmin(); }

            String confirmationCode = getInputPromt("Confirmation Code: ");
            if (confirmationCode == null) { return returnToAdmin(); }

            var bookingController = new BookingController();
            var booking = new Booking(bookingController.getMinFreeId((u) -> u.getId()), true, confirmationCode);
            bookingController.add(booking);

            new BookingPayementController().add(new BookingPayment(booking, stripeId, false));
            new BookingAgentController().add(new BookingAgent(booking, agent));
            new BookingUserController().add(new BookingUser(booking, user));
            new FlightBookingController().add(new FlightBookings(flight, booking));

            var passengerController = new PassengerController();
            for (Passenger p : passengers) {
                p.setBooking(booking);
                p.setId(passengerController.getMinFreeId((pp) -> pp.getId()));
                passengerController.add(p);
            }

            new FlightController().updateReservedSeats(flight, reservedSeats + passegerCount);

            ConnectionsManager.getConnection().commit();

        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption update() {
        try {
            BookingController bookingController = new BookingController();

            Booking booking = getModelFromController("Select booking to update: ", bookingController);
            if (booking == null) {
                return returnToAdmin();
            }

            String confirmationCode = getInputPromt("Confirmation Code (write n/a to skip): ");
            if (confirmationCode != null && !confirmationCode.equals("n/a") && !confirmationCode.equals("N/A")) {
                bookingController.updateConfirmationCode(booking, confirmationCode);
            }

            String isActive = getInputPromt("Set Active? (true or false) (write n/a to skip)");
            if (isActive != null && !isActive.equals("n/a") && !isActive.equals("N/A")) {
                bookingController.updateIsActive(booking, Boolean.parseBoolean(isActive));
            }

            Flight flight = updateModelPromt("flight", new FlightController());
            if (flight != null) {
                new FlightBookingController().refreshFromList(List.of(new FlightBookings(flight, booking)),
                        fb -> fb.getBooking().getId() == booking.getId());
            }

            User agent = updateModelPromt("booking agent: ", new UserController(),
                    (u) -> u.getRole().getId() == User.EMPLOYEE);
            if (agent != null) {
                new BookingAgentController().refreshFromList(List.of(new BookingAgent(booking, agent)),
                        ba -> ba.getBooking().getId() == booking.getId());
            }

            User user = updateModelPromt("booking user: ", new UserController(),
                    (u) -> u.getRole().getId() == User.TRAVELER);
            if (user != null) {
                new BookingUserController().refreshFromList(List.of(new BookingUser(booking, user)),
                        ba -> ba.getBooking().getId() == booking.getId());
            }

            var passengerController = new PassengerController();
            updateModelProperties("passenger", new String[] {
                "Given Name",
                "Family Name",
                "Date of Birth (yyyy-[m]m-[d]d)",
                "Gender",
                "Address",
            }, Arrays.asList(
                (passenger, arg) -> { try { passengerController.updateGivenName(passenger, arg); } catch (SQLException e) {} },
                (passenger, arg) -> { try { passengerController.updateFamilyName(passenger, arg); } catch (SQLException e) {} },
                (passenger, arg) -> { try { passengerController.updateDateOfBirth(passenger, Date.valueOf(arg)); } catch (SQLException e) {} },
                (passenger, arg) -> { try { passengerController.updateGender(passenger, arg); } catch (SQLException e) {} },
                (passenger, arg) -> { try { passengerController.updateAddress(passenger, arg); } catch (SQLException e) {} }),
                passengerController,
                (p) -> p.getBooking().getId() == booking.getId()
            );

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully updated booking details!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    @Override
    public IOption delete() {
        return deleteModel("booking", new BookingController());
    }

    @Override
    public IOption read() {
        return readModel("booking", new BookingController());
    }

    @Override
    public IOption returnToAdmin() { return previousView.entryPoint(); }

    @Override
    public IOption entryPoint() { return null; }

    private Passenger createAPassenger() {
        Passenger passenger = new Passenger();

        String givenName = getInputPromt("Given Name: ");
        if (givenName == null) { return null; }

        String familyName = getInputPromt("Family Name: ");
        if (familyName == null) { return null; }

        String dob = getInputPromt("Date of Birth (yyyy-[m]m-[d]d): ");
        if (dob == null) { return null; }

        Date dateOfBirth = Date.valueOf(dob);

        String gender = getInputPromt("Gender: ");
        if (gender == null) { return null; }

        String address = getInputPromt("Address: ");
        if (address == null) { return null; }

        passenger.setAddress(address);
        passenger.setDateOfBirth(dateOfBirth);
        passenger.setFamilyName(familyName);
        passenger.setGender(gender);
        passenger.setGivenName(givenName);

        return passenger;
    }
}
