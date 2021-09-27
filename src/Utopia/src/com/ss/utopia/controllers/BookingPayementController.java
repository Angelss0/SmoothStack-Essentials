package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.BookingPayment;

public class BookingPayementController extends Controller<BookingPayment> {

    @Override
    public String getTableName() { return "booking_payment"; }

    @Override
    public void add(BookingPayment t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getStripeId(),
            t.getRefunded()
        });
    }

    @Override
    public void delete(BookingPayment t) throws SQLException {
        delete(new String[] {
            "booking_id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected BookingPayment extractModel(ResultSet rs) throws SQLException {
        BookingPayment bookingPayment = new BookingPayment();
        bookingPayment.setBooking(new BookingController().find(rs.getInt("booking_id")));
        bookingPayment.setStripeId(rs.getString("stripe_id"));
        bookingPayment.setRefunded(rs.getBoolean("refunded"));
        return bookingPayment;
    }

    public BookingPayment find(int id) throws SQLException {
        return read(new String[] {
            "booking_id"
        }, new Object[] {
            id
        });
    }

    public void updateStripeId(BookingPayment bookingPayment, String stripeId) throws SQLException {
        update(new String[] {
            "stripe_id"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            stripeId,
            bookingPayment.getId()
        });
    }

    public void updateRefunded(BookingPayment bookingPayment, boolean refunded) throws SQLException {
        update(new String[] {
            "refunded"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            refunded,
            bookingPayment.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
