package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.UserRole;

public class UserRoleController extends Controller<UserRole> {

    @Override
    public String getTableName() { return "user_role"; }

    @Override
    public void add(UserRole t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getName()
        });
    }

    @Override
    public void delete(UserRole t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected UserRole extractModel(ResultSet rs) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setId(rs.getInt("id"));
        userRole.setName(rs.getString("name"));
        return userRole;
    }

    public UserRole findById(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public UserRole findByName(String name) throws SQLException {
        return read(new String[] {
            "name"
        }, new Object[] {
            name
        });
    }

    public void updateName(UserRole role, String name) throws SQLException {
        update(new String[] {
            "name"
        }, new String[] {
            "id"
        }, new Object[] {
            name,
            role.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
