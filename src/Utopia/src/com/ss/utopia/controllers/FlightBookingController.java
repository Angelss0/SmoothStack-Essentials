package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.FlightBookings;

public class FlightBookingController extends Controller<FlightBookings> {

    @Override
    public String getTableName() { return "flight_bookings"; }

    @Override
    public void add(FlightBookings t) throws SQLException {
        add(new Object[] {
            t.getFlight().getId(),
            t.getBooking().getId()
        });
    }

    @Override
    public void delete(FlightBookings t) throws SQLException {
        delete(new String[] {
            "flight_id",
            "booking_id"
        }, new Object[] {
            t.getFlight().getId(),
            t.getBooking().getId()
        });
    }

    @Override
    protected FlightBookings extractModel(ResultSet rs) throws SQLException {
        FlightBookings flightBooking = new FlightBookings();
        flightBooking.setFlight(new FlightController().find(rs.getInt("flight_id")));
        flightBooking.setBooking(new BookingController().find(rs.getInt("booking_id")));
        return flightBooking;
    }

    public FlightBookings find(int flightId, int bookingId) throws SQLException {
        return read(new String[] {
            "flight_id",
            "booking_id"
        }, new Object[] {
            flightId,
            bookingId
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
