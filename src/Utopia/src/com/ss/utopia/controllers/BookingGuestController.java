package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.BookingGuest;

public class BookingGuestController extends Controller<BookingGuest> {

    @Override
    public String getTableName() { return "booking_guest"; }

    @Override
    public void add(BookingGuest t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getEmail(),
            t.getPhone()
        });
    }

    @Override
    public void delete(BookingGuest t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected BookingGuest extractModel(ResultSet rs) throws SQLException {
        BookingGuest bookingGuest = new BookingGuest();
        bookingGuest.setBooking(new BookingController().find(rs.getInt("booking_id")));
        bookingGuest.setEmail(rs.getString("contact_email"));
        bookingGuest.setPhone(rs.getString("phone"));
        return bookingGuest;
    }

    public BookingGuest find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateEmail(BookingGuest bookingGuest, String email) throws SQLException {
        update(new String[] {
            "contact_email"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            email,
            bookingGuest.getId()
        });
    }

    public void updatePhone(BookingGuest bookingGuest, String phone) throws SQLException {
        update(new String[] {
            "contact_phone"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            phone,
            bookingGuest.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
