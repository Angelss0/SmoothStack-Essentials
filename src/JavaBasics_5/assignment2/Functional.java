package JavaBasics_5.assignment2;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class Functional {
    /**
     * @param intList
     * @return List of integers that represent the right-most digit of the given list.
     */
    public static List<Integer> rightDigit(List<Integer> intList) {
        return intList.stream()
            .map((val) -> val % 10)
            .collect(Collectors.toList());
    }

    /**
     * @param intList
     * @return List of integers that have been doubled.
     */
    public static List<Integer> doubling(List<Integer> intList) {
        return intList.stream()
            .map((val) -> val * 2)
            .collect(Collectors.toList());
    }

    /**
     * @param stringList
     * @return List of strings with no 'X' characters.
     */
    public static List<String> noX(List<String> stringList) {
        return stringList.stream()
            .map((val) -> val.replace("x", ""))
            .collect(Collectors.toList());
    }

    @Test
    public void rightDigitTest() {
        assertEquals("[1, 2, 3]", rightDigit(List.of(1, 22, 93)).toString());
        assertEquals("[6, 8, 6, 8, 1]", rightDigit(List.of(16, 8, 886, 8, 1)).toString());
        assertEquals("[0, 0]", rightDigit(List.of(10, 0)).toString());
    }

    @Test
    public void doublingTest() {
        assertEquals("[2, 4, 6]", doubling(List.of(1, 2, 3)).toString());
        assertEquals("[12, 16, 12, 16, -2]", doubling(List.of(6, 8, 6, 8, -1)).toString());
        assertEquals("[]", doubling(List.of()).toString());
    }

    @Test
    public void noXTest() {
        assertEquals("[a, bb, c]", noX(List.of("ax", "bb", "cx")).toString());
        assertEquals("[a, bb, c]", noX(List.of("xxax", "xbxbx", "xxcx")).toString());
        assertEquals("[]", noX(List.of("x")).toString());
    }
}
