package com.ss.utopia.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ss.utopia.controllers.*;
import com.ss.utopia.models.IModel;

public abstract class View {
    protected Scanner scanner;
    protected View previousView;

    public View(Scanner scanner, View previousView) {
        this.scanner = scanner;
        this.previousView = previousView;
    }

    protected final String RETURN_STR = "-> Return to previous";
    protected final String QUIT_STR = "-> Quit Program";

    public <T> T optionsMenu(String promt, List<String> options, List<T> objects) throws NullPointerException {
        if (options.size() == 0) { return null; }
        if (objects.size() != options.size()) { return null; }

        int answer = -1;

        System.out.println(promt + "\n");

        while (answer > objects.size() || answer < 1) {
            for (int i = 0; i < options.size(); i++)
                System.out.printf("%d) %s\n", i + 1, options.get(i));
            System.out.print("\nInput: ");

            answer = scanner.nextInt();
            if (answer > objects.size() || answer < 1) {
                System.out.println("Wrong input! Try again!\n");
            }
        }

        return objects.get(answer - 1);
    }

    public <T extends IModel> List<T> getModelsFromController(String promt, Controller<T> controller, Predicate<T> condition)
            throws SQLException, NullPointerException {
        List<T> someList = new ArrayList<>();
        T model = null;
        do {
            model = getModelFromController(promt, controller, (some) -> {
                if (condition != null) { return !someList.contains(some) && condition.test(some); }
                else {
                    return !someList.contains(some);
                }
            });
            if (model != null) {
                someList.add(model);
                model = null;
            } else {
                break;
            }
        } while (model == null);

        return someList;
    }

    public <T extends IModel> T getModelFromController(String promt, Controller<T> controller)
            throws SQLException, NullPointerException {
        return getModelFromController(promt, controller, null);
    }

    public <T extends IModel> T getModelFromController(String promt, Controller<T> controller, Predicate<T> condition)
            throws SQLException, NullPointerException {
        List<T> tObjects = controller.readAll().stream()
            .filter((tObject) -> {
                if (condition == null) { return true; }
                return condition.test(tObject);
            })
            .collect(Collectors.toList());
        List<String> tNames = tObjects.stream().map((tObject) -> tObject.getReadView()).collect(Collectors.toList());

        // System.out.println(tObjects.toString());

        tObjects.add(null);
        tNames.add(RETURN_STR);

        return optionsMenu(promt, tNames, tObjects);
    }

    public String getInputPromt(String promt) {
        String answer = "";
        System.out.println(promt);

        while (answer.equals("")) {
            answer = scanner.nextLine();
            if (answer.equals("quit")) {
                return null;
            }
        }

        return answer;
    }

    public Integer getIntPromt(String promt) {
        String answer = "";
        int _answer = -1;
        System.out.println(promt);

        while (answer.equals("")) {
            try {
                answer = scanner.nextLine();
                if (answer.equals("quit")) { return null; }
                _answer = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                answer = "";
            }
        }

        return _answer;
    }

    public Float getFloatPromt(String promt) {
        String answer = "";
        float _answer = -1;
        System.out.println(promt);

        while (answer.equals("")) {
            try {
                answer = scanner.nextLine();
                if (answer.equals("quit")) { return null; }
                _answer = Float.parseFloat(answer);
            } catch (NumberFormatException e) {
                answer = "";
            }
        }

        return _answer;
    }

    public <T extends IModel> T updateModelPromt(String modelName, Controller<T> controller) throws SQLException {
        return updateModelPromt(modelName, controller, null);
    }

    public <T extends IModel> T updateModelPromt(String modelName, Controller<T> controller, Predicate<T> condition) throws SQLException {
        String arg = getInputPromt("Update " + modelName + "? (y/N): ");
        if (arg.equals("y") || arg.equals("Y")) {
            T tObject = getModelFromController("Choose a flight: ", controller, condition);
            System.out.println();
            return tObject;
        }
        return null;
    }

    public IOption promter(String msg) {
        System.out.println(msg);
        return () -> null;
    }

    public abstract IOption entryPoint();
}
