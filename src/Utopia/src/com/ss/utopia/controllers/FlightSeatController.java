package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.FlightSeat;

public class FlightSeatController extends Controller<FlightSeat> {

    @Override
    public String getTableName() { return "flight_seats"; }

    @Override
    public void add(FlightSeat flightSeat) throws SQLException {
        add(new Object[] {
            flightSeat.getId(),
            flightSeat.getCabinType(),
            flightSeat.getAvailableSeats()
        });
    }

    @Override
    public void delete(FlightSeat flightSeat) throws SQLException {
        delete(new String[] {
            "flight_id",
            "cabin_type"
        }, new Object[] {
            flightSeat.getId(),
            flightSeat.getCabinType(),
        });
    }

    public void updateAvailableSeats(FlightSeat flightSeat, int seats) throws SQLException {
        update(new String[] {
            "available_seats"
        }, new String[] {
            "flight_id",
            "cabin_type"
        }, new Object[] {
            seats,
            flightSeat.getId(),
            flightSeat.getCabinType()
        });
    }

    @Override
    protected FlightSeat extractModel(ResultSet rs) throws SQLException {
        FlightSeat flightSeat = new FlightSeat();
        flightSeat.setFlight(new FlightController().find(rs.getInt("flight_id")));
        flightSeat.setCabinType(rs.getString("cabin_type"));
        flightSeat.setAvailableSeats(rs.getInt("available_seats"));
        return flightSeat;
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
