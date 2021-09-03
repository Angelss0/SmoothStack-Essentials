package JavaBasics_5.assignment1;

import java.util.*;
import java.util.stream.*;

public class BasicLambdas {    
    /**
     * Sorts by having strings that start with the letter 'e' be first,
     * and every other element second.
     * @param s1
     * @param s2
     * @return
     */
    public static int returnFirstE(String s1, String s2) {
        return s1.charAt(0) == 'e' ? -1 : 0;
    }

    /**
     * An example of various ways to sort using lambdas.
     */
    public static void basicLambdaSorting() {
        String[] words = { "ABC", "everything", "every", "_All things may be good...", "Some", "No things" };

        // Length
        Arrays.sort(words, (s1, s2) -> s1.length() - s2.length());
        System.out.println(List.of(words).toString());

        // Reverse Length
        Arrays.sort(words, (s1, s2) -> s2.length() - s1.length());
        System.out.println(List.of(words).toString());

        // Alphabetically
        Arrays.sort(words, (s1, s2) -> s1.charAt(0) - s2.charAt(0));
        System.out.println(List.of(words).toString());

        // First is one that contains e.
        Arrays.sort(words, (s1, s2) -> s1.charAt(0) == 'e' ? -1 : 0);
        System.out.println(List.of(words).toString());

        // Length again to reset
        Arrays.sort(words, (s1, s2) -> s1.length() - s2.length());
        System.out.println(List.of(words).toString());

        // First is one that contains e.
        Arrays.sort(words, (s1, s2) -> returnFirstE(s1, s2));
        System.out.println(List.of(words).toString());
    }

    /**
     * Takes in an integer list and returns a comma seperated string.
     * @param integers
     * @return
     */
    public static String returnCommaSeperatedString(List<Integer> integers) {
        StringJoiner stringJoiner = new StringJoiner(",");

        integers.forEach((w) -> stringJoiner.add((w % 2 > 0 ? "e" : "o") + w.toString()));
        return stringJoiner.toString();
    }

    /**
     * Takes a list of Strings and returns a filtered list of strings that
     * start with the letter 'a' and are 3 leters long.
     * @param list
     * @return
     */
    public static List<String> return3LengthAStrings(List<String> list) {
        List<String> strings = list.stream()
                .filter((word) -> (word.charAt(0) == 'a') && (word.length() == 3))
                .collect(Collectors.toList());

        return strings;
    }
}
