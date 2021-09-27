package com.ss.utopia.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ss.utopia.models.User;
import com.ss.utopia.models.UserRole;
import com.ss.utopia.util.AnsiFormat;
import com.ss.utopia.util.AnsiFormat.Colors;

public class UserController extends Controller<User> {

    @Override
    public String getTableName() { return "user"; }

    @Override
    public void add(User t) throws SQLException {
        add(new Object[] {
            t.getId(),
            t.getRole().getId(),
            t.getGivenName(),
            t.getFamilyName(),
            t.getUsername(),
            t.getEmail(),
            t.getPassword(),
            t.getPhone()
        });
    }

    @Override
    public void delete(User t) throws SQLException {
        delete(new String[] {
            "id"
        }, new Object[] {
            t.getId()
        });
    }

    @Override
    protected User extractModel(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setRole(new UserRoleController().findById(rs.getInt("role_id")));
        user.setGivenName(rs.getString("given_name"));
        user.setFamilyName(rs.getString("family_name"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setPhone(rs.getString("phone"));
        return user;
    }

    public User find(int id) throws SQLException {
        return read(new String[] {
            "id"
        }, new Object[] {
            id
        });
    }

    public User createNewUser(int freeId, String[] args, int userId) throws SQLException, NullPointerException {
        User user = new User();
        user.setId(freeId);
        user.setGivenName(args[0]);
        user.setFamilyName(args[1]);
        user.setUsername(args[2]);
        user.setEmail(args[3]);
        user.setPassword(args[4]);
        user.setPhone(args[5]);

        UserRole role = new UserRoleController().findById(userId);

        if (role == null) {
            System.out.println(AnsiFormat.setColor("No role found with that name!", Colors.Yellow));
            return user;
        }

        user.setRole(role);
        return user;
    }

    public void updateRole(User user, UserRole role) throws SQLException {
        update(new String[] {
            "role_id"
        }, new String[] {
            "id"
        }, new Object[] {
            role.getId(),
            user.getId()
        });
    }

    public void updateGivenName(User user, String givenName) throws SQLException {
        update(new String[] {
            "given_name"
        }, new String[] {
            "id"
        }, new Object[] {
            givenName,
            user.getId()
        });
    }

    public void updateFamilyName(User user, String familyName) throws SQLException {
        update(new String[] {
            "family_name"
        }, new String[] {
            "id"
        }, new Object[] {
            familyName,
            user.getId()
        });
    }

    public void updateUsername(User user, String username) throws SQLException {
        update(new String[] {
            "username"
        }, new String[] {
            "id"
        }, new Object[] {
            username,
            user.getId()
        });
    }
    public void updateEmail(User user, String email) throws SQLException {
        update(new String[] {
            "email"
        }, new String[] {
            "id"
        }, new Object[] {
            email,
            user.getId()
        });
    }
    public void updatePassword(User user, String password) throws SQLException {
        update(new String[] {
            "password"
        }, new String[] {
            "id"
        }, new Object[] {
            password,
            user.getId()
        });
    }

    public void updatePhone(User user, String phone) throws SQLException {
        update(new String[] {
            "phone"
        }, new String[] {
            "id"
        }, new Object[] {
            phone,
            user.getId()
        });
    }

    @Override
    protected boolean hasAutoincrement() { return true; }
}
