package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Airport;
import com.ss.utopia.models.Route;

public class RouteController extends Controller<Route> {

    @Override
    public String getTableName() { return "route"; }

    @Override
    public void add(Route t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getOrigin().getIataID(),
            t.getDestination().getIataID()
        });
    }

    @Override
    public void delete(Route t) throws SQLException {
        delete(new String[] {
            "id",
            "origin_id",
            "destination_id"
        }, new Object[] {
            t.getId(),
            t.getOrigin().getIataID(),
            t.getDestination().getIataID()
        });
    }

    @Override
    protected Route extractModel(ResultSet rs) throws SQLException {
        Route route = new Route();
        route.setId(rs.getInt("id"));
        route.setOrigin(new AirportController().find(rs.getString("origin_id")));
        route.setDestination(new AirportController().find(rs.getString("destination_id")));
        return route;
    }

    public Route findById(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public Route findByAirports(Airport origin, Airport destination) throws SQLException {
        return read(new String[] {
            "origin_id",
            "destination_id"
        }, new Object[] {
            origin.getIataID(),
            destination.getIataID()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
