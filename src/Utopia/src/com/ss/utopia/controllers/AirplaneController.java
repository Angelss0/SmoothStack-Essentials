package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.Airplane;
import com.ss.utopia.models.AirplaneType;

public class AirplaneController extends Controller<Airplane> {

    @Override
    public String getTableName() { return "airplane"; }

    @Override
    public void add(Airplane t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getAirplaneType().getId()
        });
    }

    @Override
    public void delete(Airplane t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected Airplane extractModel(ResultSet rs) throws SQLException {
        Airplane airplane = new Airplane();
        airplane.setId(rs.getInt("id"));
        airplane.setAirplaneType(new AirplaneTypeController().find(rs.getInt("type_id")));
        return airplane;
    }

    public Airplane find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public void updateType(Airplane airplane, AirplaneType airplaneType) throws SQLException {
        update(new String[] {
            "type_id"
        }, new String[] {
            "id"
        }, new Object[] {
            airplaneType.getId(),
            airplane.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
