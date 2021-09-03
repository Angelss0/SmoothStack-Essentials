package JavaBasics_5.assignment1;

import java.util.*;
import java.time.*;

public class assignment1 {

    public static void main(String[] args) {
        // Basic Lambda Functions
        BasicLambdas.basicLambdaSorting();
        String result0 = BasicLambdas.returnCommaSeperatedString(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        List<String> result1 = BasicLambdas.return3LengthAStrings(List.of(
            "abc", "acb",
            "some random string?", "ah",
            "Too many strings", "dsafafgaer",
            "asd", "asdgsh", "teh", "hgf",
            "sag", "fds", "fht", "aut"
        ));

        System.out.println(result0);
        System.out.println(result1.toString());

        // Date API functions
        DateAPI.getPreviousThursday(LocalDate.now());
        System.out.println(DateAPI.getMonthLengthsFromYear(2021).toString());
        System.out.println(DateAPI.getMondaysCountFromMonth(Month.JANUARY.getValue()));

        System.out.println(DateAPI.isDateFridayThe13th(LocalDate.now()));
        System.out.println(DateAPI.isDateFridayThe13th(LocalDate.of(2021, 8, 13)));
    }
}