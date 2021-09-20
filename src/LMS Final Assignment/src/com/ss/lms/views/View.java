package com.ss.lms.views;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ss.lms.controllers.*;
import com.ss.lms.models.IModel;

public class View {
    protected Scanner scanner;

    protected AuthorController authorController = new AuthorController();
    protected BookController bookController = new BookController();
    protected BookAuthorsController bookAuthorsController = new BookAuthorsController();
    protected BookCopiesController copiesController = new BookCopiesController();
    protected BookLoansController loansController = new BookLoansController();
    protected BookGenresController bookGenreController = new BookGenresController();
    protected BorrowerController borrowerController = new BorrowerController();
    protected GenreController genreController = new GenreController();
    protected LibraryBranchController branchController = new LibraryBranchController();
    protected PublisherController publisherController = new PublisherController();

    public View(Scanner scanner) {
        this.scanner = scanner;
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
        List<String> tNames = tObjects.stream().map((tObject) -> tObject.getMenuRep()).collect(Collectors.toList());

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

    public IOption promter(String msg) {
        System.out.println(msg);
        return () -> null;
    }
}
