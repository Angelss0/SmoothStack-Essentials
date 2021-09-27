package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Airport;

public class AirportController extends Controller<Airport> {

    @Override
    public String getTableName() { return "airport"; }

    @Override
    public void add(Airport t) throws SQLException {
        add(new Object[] {
            t.getIataID(),
            t.getCityName()
        });
    }

    @Override
    public void delete(Airport t) throws SQLException {
        delete(new String[] {
            "iata_id"
        }, new Object[] {
            t.getCityName()
        });
    }

    @Override
    protected Airport extractModel(ResultSet rs) throws SQLException {
        Airport airport = new Airport();
        airport.setIataID(rs.getString("iata_id"));
        airport.setCityName(rs.getString("city"));
        return airport;
    }

    public Airport find(String iata_id) throws SQLException {
        return read(new String[] {
            "iata_id"
        }, new Object[] {
            iata_id
        });
    }

    public void updateCity(Airport airport, String cityName) throws SQLException {
        update(new String[] {
            "city"
        }, new String[] {
            "iata_id"
        }, new Object[] {
            cityName,
            airport.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return false; }
}
