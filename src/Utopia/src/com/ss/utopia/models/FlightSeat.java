package com.ss.utopia.models;

public class FlightSeat implements IModel {

    private Flight flight;
    private String cabinType;
    private int availableSeats;

    public final static String FIRST = "first";
    public final static String BUSSINESS = "bussiness";
    public final static String ECONOMY = "economy";

    @Override
    public int getId() { return flight.getId(); }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public String getCabinType() { return cabinType; }
    public void setCabinType(String cabinType) { this.cabinType = cabinType; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int reservedSeats) { this.availableSeats = reservedSeats; }

    @Override
    public String getReadView() {
        return getCabinType() + " -> " + getAvailableSeats();
    }

}
