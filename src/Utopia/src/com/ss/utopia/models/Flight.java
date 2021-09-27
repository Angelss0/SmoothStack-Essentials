package com.ss.utopia.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight implements IModel {
    private int id;
    private Route route;
    private Airplane airplane;
    private Timestamp departureTime;
    private int reservedSeats;
    private float seatPrice;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getSeatPrice() { return seatPrice; }
    public void setSeatPrice(float seatPrice) { this.seatPrice = seatPrice; }

    public int getReservedSeats() { return reservedSeats; }
    public void setReservedSeats(int reservedSeats) { this.reservedSeats = reservedSeats; }

    public Timestamp getDepartureTime() { return departureTime; }
    public void setDepartureTime(Timestamp departureTime) { this.departureTime = departureTime; }

    public Airplane getAirplane() { return airplane; }
    public void setAirplane(Airplane airplane) { this.airplane = airplane; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    @Override
    public String getReadView() {
        return getAirplane().getReadView() + ", " + getRoute().getReadView() + ", departure time: " + getDepartureTime();
    }

    @Override
    public String toString() {
        LocalDateTime departureTime = getDepartureTime().toLocalDateTime();

        return "Departure Airport: " + getRoute().getOrigin().getReadView() +
        " | Arrival Airport: " + getRoute().getDestination().getReadView() + "\n" +
        "Departure Date: " + departureTime.format(DateTimeFormatter.ISO_LOCAL_DATE) +
        " | Departure Time: " + departureTime.format(DateTimeFormatter.ISO_LOCAL_TIME) + "\n" +
        "Arrival Date: " + departureTime.format(DateTimeFormatter.ISO_LOCAL_DATE) +
        " | Arrival Time: " + departureTime.plusHours(2).format(DateTimeFormatter.ISO_LOCAL_TIME)
        ;
    }
}
