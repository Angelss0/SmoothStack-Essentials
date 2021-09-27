package com.ss.utopia.models;

public class FlightBookings {
    private Flight flight;
    private Booking booking;

    public FlightBookings() {}
    public FlightBookings(Flight flight, Booking booking) {
        this.flight = flight;
        this.booking = booking;
    }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public Booking getBooking() { return booking; }
    public void setBooking(Booking booking) { this.booking = booking; }

    @Override
    public String toString() { return getFlight().getReadView() + "\n" + getBooking().getReadView(); }
}