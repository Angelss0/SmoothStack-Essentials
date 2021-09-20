package com.ss.lms.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.lms.models.Publisher;

public class PublisherController extends Controller<Publisher> {

    public PublisherController() {
        super();
        tableName = "tbl_publisher";
    }

    @Override
    public void add(Publisher publisher) throws SQLException {
        add(new Object[] {
            publisher.getID(),
            publisher.getName(),
            publisher.getAddress(),
            publisher.getPhone()
        });
    }

    @Override
    public void delete(Publisher publisher) throws SQLException {
        delete(new String[] {
            "publisherId"
        }, new Object[] {
            publisher.getID()
        });
    }

    @Override
    protected Publisher extractModel(ResultSet rs) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setID(rs.getInt("publisherId"));
        publisher.setName(rs.getString("publisherName"));
        publisher.setAddress(rs.getString("publisherAddress"));
        publisher.setPhone(rs.getString("publisherPhone"));
        return publisher;
    }

    public Publisher find(int publisherId) throws SQLException {
        return read(new String[] {
            "publisherId"
        }, new Object[] {
            publisherId
        });
    }
    
    public void updateId(Publisher publisher, int id) throws SQLException {
        update(new String[] {
            "publisherId"
        }, new String[] {
            "publisherId"
        }, new Object[] {
            id,
            publisher.getID()
        });
    }

    public void updateName(Publisher publisher, String name) throws SQLException {
        update(new String[] {
            "publisherName"
        }, new String[] {
            "publisherId"
        }, new Object[] {
            name,
            publisher.getID()
        });
    }

    public void updateAddress(Publisher publisher, String address) throws SQLException {
        update(new String[] {
            "publisherAddress"
        }, new String[] {
            "publisherId"
        }, new Object[] {
            address,
            publisher.getID()
        });
    }

    public void updatePhone(Publisher publisher, String phone) throws SQLException {
        update(new String[] {
            "publisherPhone"
        }, new String[] {
            "publisherId"
        }, new Object[] {
            phone,
            publisher.getID()
        });
    }
}
