package JavaBasics_5.assignment2;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;
import org.junit.Test;

public class Lambdas {
    
    @FunctionalInterface
    interface PerformOperation {
        public String invoke(int number);
    }
    
    public static PerformOperation isOdd() { return (n) -> n % 2 > 0 ? "ODD" : "EVEN"; }
    public static PerformOperation isPrime() {
        return (n) -> {
            if (n <= 1) { return "COMPOSITE"; }

            for (int i = 2; i < n; i++)
                if (n % i == 0)
                    return "COMPOSITE";

            return "PRIME";
        };
    }
    public static PerformOperation isPalindrome() {
        return (num) -> {
            String stringedDigits = Integer.toString(num);
            int digitCount = stringedDigits.length();

            for (int i = 0; i < digitCount; i++) {
                if (stringedDigits.charAt(i) != stringedDigits.charAt(digitCount-1))
                    return "PALINDROME";
            }
            return "NOT PALINDROME";
        };
    }

    @Test
    public void LambdasTest() {
        Scanner file = new Scanner("5\n1 4\n2 5\n3 898\n1 3\n2 12\n");    
        int testCases = file.nextInt();
        String someString = "";

        for (int i = 0; i < testCases; i++)
        {
            int operationType = file.nextInt();
            String result = "";

            if (operationType == 1)
                result = isOdd().invoke(file.nextInt());
            else if (operationType == 2)
                result = isPrime().invoke(file.nextInt());
            else if (operationType == 3)
                result = isPalindrome().invoke(file.nextInt());

            someString = someString + result + "\n";
        }
        file.close();

        assertEquals("EVEN\nPRIME\nPALINDROME\nODD\nCOMPOSITE\n", someString);
    }
}
