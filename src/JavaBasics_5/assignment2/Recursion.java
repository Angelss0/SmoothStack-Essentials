package JavaBasics_5.assignment2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.*;
// import java.util.stream.Collectors;

import org.junit.Test;

public class Recursion {
    /**
     * @param min
     * @param intArray
     * @param max
     * @return true if a list can sum up to max, false if else.
     */
    public static boolean groupSumClump(int min, Integer[] intArray, int max) {
        if (min >= max || intArray.length == 0)
            return false;

        List<Integer> intList = new ArrayList<Integer>(List.of(intArray));

        int replaceInt = intList.get(0);
        for(int i = 0; i < intList.size() - 1; i++) {
            replaceInt = intList.get(i);
            
            while (intList.get(i) == intList.get(i + 1)) {
                replaceInt += intList.get(i);
                intList.remove(i + 1);
                
                if (i + 1 > intList.size() - 1)
                    break;
            }

            intList.set(i, replaceInt);
        }

        List<Integer> listCopy = new ArrayList<Integer>(intList);
        intList.sort((s1, s2) -> s1 - s2);

        while (intList.get(intList.size() - 1) > max) {
            listCopy.remove(intList.get(intList.size() - 1));
            intList.remove(intList.size() - 1);
        }

        Integer maxValue = intList.get(intList.size() - 1);
        Integer minValue = intList.get(0);

        if ((maxValue + minValue) == max) {
            return true;
        } else {
            listCopy.remove(maxValue);
            listCopy.remove(minValue);
            return groupSumClump(minValue, listCopy.toArray(new Integer[0]), maxValue);
        }
    }

    @Test
    public void groupSumClumpTest() {
        assertTrue("1", groupSumClump(0, new Integer[] {2, 4, 4, 4, 4, 4, 8}, 10));
        assertTrue("2", groupSumClump(0, new Integer[] {1, 2, 4, 8, 1}, 14));
        assertFalse("3", groupSumClump(0, new Integer[] {2, 4, 4, 8}, 14));
    }
}
