package com.ss.utopia.controllers;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Airplane;
import com.ss.utopia.models.Flight;
import com.ss.utopia.models.Route;

public class FlightController extends Controller<Flight> {

    @Override
    public String getTableName() { return "flight"; }

    @Override
    public void add(Flight t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getRoute().getId(),
            t.getAirplane().getId(),
            t.getDepartureTime(),
            t.getReservedSeats(),
            t.getSeatPrice()
        });
    }

    @Override
    public void delete(Flight t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected Flight extractModel(ResultSet rs) throws SQLException {
        Flight flight = new Flight();
        flight.setId(rs.getInt("id"));
        flight.setRoute(new RouteController().findById(rs.getInt("route_id")));
        flight.setAirplane(new AirplaneController().find(rs.getInt("airplane_id")));
        flight.setDepartureTime(rs.getTimestamp("departure_time"));
        flight.setReservedSeats(rs.getInt("reserved_seats"));
        flight.setSeatPrice(rs.getFloat("seat_price"));
        return flight;
    }

    public Flight find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateRoute(Flight flight, Route route) throws SQLException {
        update(new String[] {
            "route_id"
        }, new String[] {
            "id"
        }, new Object[] {
            route.getId(),
            flight.getId()
        });
    }

    public void updateAirplane(Flight flight, Airplane airplane) throws SQLException {
        update(new String[] {
            "airplane_id"
        }, new String[] {
            "id"
        }, new Object[] {
            airplane.getId(),
            flight.getId()
        });
    }

    public void updateDepartureTime(Flight flight, Timestamp departureTime) throws SQLException {
        update(new String[] {
            "departure_time"
        }, new String[] {
            "id"
        }, new Object[] {
            departureTime,
            flight.getId()
        });
    }

    public void updateReservedSeats(Flight flight, int reservedSeats) throws SQLException {
        update(new String[] {
            "reserved_seats"
        }, new String[] {
            "id"
        }, new Object[] {
            reservedSeats,
            flight.getId()
        });
    }

    public void updateSeatPrice(Flight flight, Float seatPrice) throws SQLException {
        update(new String[] {
            "seat_price"
        }, new String[] {
            "id"
        }, new Object[] {
            seatPrice,
            flight.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
