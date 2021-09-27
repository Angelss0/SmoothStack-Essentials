package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Booking;

public class BookingController extends Controller<Booking> {

    @Override
    public String getTableName() { return "booking"; }

    @Override
    public void add(Booking t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getIsActive(),
            t.getConfirmationCode()
        });
    }

    @Override
    public void delete(Booking t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected Booking extractModel(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getInt("id"));
        booking.setIsActive(rs.getBoolean("is_active"));
        booking.setConfirmationCode(rs.getString("confirmation_code"));
        return booking;
    }

    public Booking find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateIsActive(Booking booking, boolean isActive) throws SQLException {
        update(new String[] {
            "is_active"
        }, new String[] {
            "id"
        }, new Object[] {
            isActive,
            booking.getId()
        });
    }

    public void updateConfirmationCode(Booking booking, String confirmationCode) throws SQLException {
        update(new String[] {
            "confirmation_code"
        }, new String[] {
            "id"
        }, new Object[] {
            confirmationCode,
            booking.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
