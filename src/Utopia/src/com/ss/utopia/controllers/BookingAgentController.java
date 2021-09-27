package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.BookingAgent;
import com.ss.utopia.models.User;

public class BookingAgentController extends Controller<BookingAgent> {

    @Override
    public String getTableName() { return "booking_agent"; }

    @Override
    public void add(BookingAgent t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getAgent().getId()
        });
    }

    @Override
    public void delete(BookingAgent t) throws SQLException {
        delete(new String[] {
            "booking_id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected BookingAgent extractModel(ResultSet rs) throws SQLException {
        BookingAgent bookingAgent = new BookingAgent();
        bookingAgent.setBooking(new BookingController().find(rs.getInt("booking_id")));
        bookingAgent.setAgent(new UserController().find(rs.getInt("agent_id")));
        return bookingAgent;
    }

    public BookingAgent find(int id) throws SQLException {
        return read(new String[] {
            "booking_id"
        }, new Object[] {
            id
        });
    }

    public void updateAgentId(BookingAgent bookingAgent, User agent) throws SQLException {
        update(new String[] {
            "agent_id"
        }, new String[] {
            "booking_id"
        }, new Object[] {
            agent.getId(),
            bookingAgent.getAgent().getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
