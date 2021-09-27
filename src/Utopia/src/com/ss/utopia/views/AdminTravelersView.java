package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import com.ss.utopia.controllers.UserController;
import com.ss.utopia.models.User;
import com.ss.utopia.util.ConnectionsManager;

public class AdminTravelersView extends BaseAdminView {

    public AdminTravelersView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    @Override
    public IOption add() {
        try {
            int freeId = new UserController().getMinFreeId((b) -> b.getId());

            return addModel("Traveler", new String[] {
                "Given name",
                "Family name",
                "Username",
                "Email",
                "Password",
                "Phone"
            }, (args) -> { try {
                return new UserController().createNewUser(freeId, args, User.TRAVELER);
            } catch (SQLException e) {
                e.printStackTrace();
                ConnectionsManager.rollbackConnection();
                return null;
            }}
            , new UserController());
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return null;
    }

    @Override
    public IOption delete() {
        return deleteModel("Traveler", new UserController(), (user) -> user.getRole().getId() == User.TRAVELER);
    }

    @Override
    public IOption read() {
        return readModel("traveler", new UserController(), (user) -> user.getRole().getId() == User.TRAVELER);
    }

    @Override
    public IOption update() {
        var controller = new UserController();

        return updateModel("employee", new String[] {
            "Given name",
            "Family name",
            "Username",
            "Email",
            "Password",
            "Phone"
        }, Arrays.asList(
            (u, arg) -> { try { controller.updateGivenName(u, arg); } catch (SQLException e) {} },
            (u, arg) -> { try { controller.updateFamilyName(u, arg); } catch (SQLException e) {} },
            (u, arg) -> { try { controller.updateUsername(u, arg); } catch (SQLException e) {} },
            (u, arg) -> { try { controller.updateEmail(u, arg); } catch (SQLException e) {} },
            (u, arg) -> { try { controller.updatePassword(u, arg); } catch (SQLException e) {} },
            (u, arg) -> { try { controller.updatePhone(u, arg); } catch (SQLException e) {} }
        ),  controller,
            (u) -> u.getRole().getId() == User.TRAVELER
        );
    }

    @Override
    public IOption returnToAdmin() { return previousView.entryPoint(); }

    @Override
    public IOption entryPoint() { return null; }

}
