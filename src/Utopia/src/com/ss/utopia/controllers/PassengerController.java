package com.ss.utopia.controllers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Booking;
import com.ss.utopia.models.Passenger;

public class PassengerController extends Controller<Passenger> {

    @Override
    public String getTableName() { return "passenger"; }

    @Override
    public void add(Passenger t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getBooking().getId(),
            t.getGivenName(),
            t.getFamilyName(),
            t.getDateOfBirth(),
            t.getGender(),
            t.getAddress()
        });
    }

    @Override
    public void delete(Passenger t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected Passenger extractModel(ResultSet rs) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setId(rs.getInt("id"));
        passenger.setBooking(new BookingController().find(rs.getInt("booking_id")));
        passenger.setGivenName(rs.getString("given_name"));
        passenger.setFamilyName(rs.getString("family_name"));
        passenger.setDateOfBirth(rs.getDate("dob"));
        passenger.setGender(rs.getString("gender"));
        passenger.setAddress(rs.getString("address"));
        return passenger;
    }

    public Passenger find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateBooking(Passenger passenger, Booking booking) throws SQLException {
        update(new String[] {
            "booking_id"
        }, new String[] {
            "id"
        }, new Object[] {
            booking.getId(),
            passenger.getId()
        });
    }

    public void updateGivenName(Passenger passenger, String givenName) throws SQLException {
        update(new String[] {
            "given_name"
        }, new String[] {
            "id"
        }, new Object[] {
            givenName,
            passenger.getId()
        });
    }

    public void updateFamilyName(Passenger passenger, String familyName) throws SQLException {
        update(new String[] {
            "family_name"
        }, new String[] {
            "id"
        }, new Object[] {
            familyName,
            passenger.getId()
        });
    }

    public void updateDateOfBirth(Passenger passenger, Date dateOfBirth) throws SQLException {
        update(new String[] {
            "dob"
        }, new String[] {
            "id"
        }, new Object[] {
            dateOfBirth,
            passenger.getId()
        });
    }

    public void updateGender(Passenger passenger, String gender) throws SQLException {
        update(new String[] {
            "gender"
        }, new String[] {
            "id"
        }, new Object[] {
            gender,
            passenger.getId()
        });
    }

    public void updateAddress(Passenger passenger, String address) throws SQLException {
        update(new String[] {
            "address"
        }, new String[] {
            "id"
        }, new Object[] {
            address,
            passenger.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
