package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.AirplaneType;

public class AirplaneTypeController extends Controller<AirplaneType> {

    @Override
    public String getTableName() { return "airplane_type"; }

    @Override
    public void add(AirplaneType t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getMaxCapacity()
        });
    }

    @Override
    public void delete(AirplaneType t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected AirplaneType extractModel(ResultSet rs) throws SQLException {
        AirplaneType airplaneType = new AirplaneType();
        airplaneType.setId(rs.getInt("id"));
        airplaneType.setMaxCapacity(rs.getInt("max_capacity"));
        return airplaneType;
    }

    public AirplaneType find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateCapacity(AirplaneType airplaneType, int maxCapacity) throws SQLException {
        update(new String[] {
            "max_capacity"
        }, new String[] {
            "id"
        }, new Object[] {
            maxCapacity,
            airplaneType.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
