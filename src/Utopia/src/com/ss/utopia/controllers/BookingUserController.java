package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.BookingUser;
import com.ss.utopia.models.User;

public class BookingUserController extends Controller<BookingUser> {

    @Override
    public String getTableName() { return "booking_user"; }

    @Override
    public void add(BookingUser t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getUser().getId()
        });
    }

    @Override
    public void delete(BookingUser t) throws SQLException {
        delete(new String[] {
            "booking_id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected BookingUser extractModel(ResultSet rs) throws SQLException {
        BookingUser bookUser = new BookingUser();
        bookUser.setBooking(new BookingController().find(rs.getInt("booking_id")));
        bookUser.setUser(new UserController().find(rs.getInt("user_id")));
        return bookUser;
    }

    public BookingUser find(int id) throws SQLException {
        return read(new String[] {
            "booking_id"
        }, new Object[] {
            id
        });
    }

    public void updateAgentId(BookingUser bookingUser, User user) throws SQLException {
        update(new String[] {
            "user_id"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            user.getId(),
            bookingUser.getUser().getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
