package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ss.utopia.controllers.Controller;
import com.ss.utopia.models.IModel;
import com.ss.utopia.util.ConnectionsManager;

public abstract class BaseAdminView extends View {
    public BaseAdminView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    public abstract IOption add();
    public abstract IOption update();
    public abstract IOption delete();
    public abstract IOption read();

    public abstract IOption returnToAdmin();

    public <T extends IModel> IOption addModel(String modelName, String[] propertyNames,
            Function<String[], T> setOptions, Controller<T> controller) {
        try {
            String[] args = new String[propertyNames.length];
            for (int i = 0; i < propertyNames.length; i++) {
                String arg = getInputPromt(propertyNames[i] + ": ");
                if (arg == null) { return returnToAdmin(); }
                args[i] = arg;

                System.out.println();
            }

            T tObject = setOptions.apply(args);
            controller.add(tObject);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully added a " + modelName + "!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    public <T extends IModel> IOption deleteModel(String modelName, Controller<T> controller) {
        return deleteModel(modelName, controller, null);
    }

    public <T extends IModel> IOption deleteModel(String modelName, Controller<T> controller, Predicate<T> condition) {
        try {
            T tObject = getModelFromController("Select " + modelName + " to delete:", controller, condition);
            if (tObject == null) { return returnToAdmin(); }

            String name = getInputPromt("Are you sure you want to delete the " + modelName + " (y/N)?:");
            if (!name.equals("Y") && !name.equals("y")) { return returnToAdmin(); }
            controller.delete(tObject);

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully deleted a " + modelName + "!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    public <T extends IModel> IOption readModel(String modelName, Controller<T> controller) {
        return readModel(modelName, controller, null);
    }

    public <T extends IModel> IOption readModel(String modelName, Controller<T> controller, Predicate<T> condition) {
        try {
            T tObject = getModelFromController("Select " + modelName + " To read about:", controller, condition);
            if (tObject == null) { return returnToAdmin(); }

            System.out.println(tObject.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }
        return returnToAdmin();
    }

    public <T extends IModel> IOption updateModel(String modelName, String[] propertyNames,
        List<BiConsumer<T, String>> setOptions, Controller<T> controller) {
            return updateModel(modelName, propertyNames, setOptions, controller, null);
    }

    public <T extends IModel> IOption updateModel(String modelName, String[] propertyNames,
            List<BiConsumer<T, String>> setOptions, Controller<T> controller, Predicate<T> condition) {
        try {
            T tObject = getModelFromController("Select " + modelName + " to update: ", controller, condition);
            if (tObject == null) { return returnToAdmin(); }

            for (int i = 0; i < propertyNames.length; i++) {
                String arg = getInputPromt(propertyNames[i] + " (or write n/a to ignore) : ");
                if (arg == null) { return returnToAdmin(); }
                else if (!arg.equals("n/a") && !arg.equals("N/A")) {
                    setOptions.get(i).accept(tObject, arg);
                    System.out.println();
                }
            }

            ConnectionsManager.getConnection().commit();
            System.out.println("Successfully updated " + modelName + " details!");
        } catch (SQLException e) {
            e.printStackTrace();
            ConnectionsManager.rollbackConnection();
        }

        return returnToAdmin();
    }

    public <T extends IModel> T updateModelProperties(String modelName, String[] propertyNames,
            List<BiConsumer<T, String>> setOptions, Controller<T> controller, Predicate<T> condition)
            throws SQLException {

        T tObject = getModelFromController("Select " + modelName + " to update: ", controller, condition);
        if (tObject == null) {
            return null;
        }

        for (int i = 0; i < propertyNames.length; i++) {
            String arg = getInputPromt(propertyNames[i] + " (or write n/a to ignore) : ");
            if (arg == null) {
                return null;
            } else if (!arg.equals("n/a") && !arg.equals("N/A")) {
                setOptions.get(i).accept(tObject, arg);
                System.out.println();
            }
        }

        return tObject;
    }

    public <T extends IModel, V, R> List<R> selectMultipleModels(String promt, V object, Controller<T> controller,
            BiFunction<Object, V, R> setOptions, Function<R, T> mapper) throws SQLException {
        var rList = new ArrayList<R>();
        T tObject = null;
        do {
            tObject = getModelFromController(promt, controller,
                    (a) -> !rList.stream().map(mapper).collect(Collectors.toList()).contains(a));

            if (tObject != null) {
                R rObject = setOptions.apply(tObject, object);
                rList.add(rObject);
            }
        } while (tObject != null);
        return rList;
    }
}
