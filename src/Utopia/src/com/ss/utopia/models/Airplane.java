package com.ss.utopia.models;

import java.util.List;

public class Airplane implements IModel {
    private int id;
    private AirplaneType airplaneType;
    private List<Flight> flights;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public AirplaneType getAirplaneType() { return airplaneType; }
    public void setAirplaneType(AirplaneType airplaneType) { this.airplaneType = airplaneType; }

    public List<Flight> getFlights() { return flights; }
    public void setFlights(List<Flight> flights) { this.flights = flights; }

    @Override
    public String getReadView() { return getId() + ", " + getAirplaneType().getReadView(); }

    @Override
    public String toString() { return getReadView(); }
}
