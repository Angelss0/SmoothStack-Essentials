package com.ss.utopia.models;

import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

import com.ss.utopia.controllers.PassengerController;

public class Booking implements IModel {
    private int id;
    private boolean isActive;
    private String confirmationCode;

    private List<Passenger> passengers;

    public Booking() {};

    public Booking(int id, boolean isActive, String code) {
        this.id = id;
        this.isActive = isActive;
        this.confirmationCode = code;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean getIsActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }

    public String getConfirmationCode() { return confirmationCode; }
    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }

    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }

    @Override
    public String getReadView() { return getConfirmationCode() + ", is Active: " + getIsActive(); }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("\n\n");
        try {
            new PassengerController().readAll()
                .stream()
                .filter((p) -> p.getBooking().getId() == getId())
                .forEach(p -> stringJoiner.add(p.toString()));
        } catch (SQLException e) { e.printStackTrace(); }
        return "Confirmation Code: " + getReadView() + "\n\n" + "Passengers:\n" + stringJoiner.toString();
    }
}
