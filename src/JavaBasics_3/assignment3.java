package JavaBasics_3;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class assignment3 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid Arguments. Specify a file path (Abosolute or relative) and char value.");
            return;
        }
    
        int occurances = 0;

        try {
            FileReader fileReader = new FileReader(args[0]);

            int currentChar;
            while ((currentChar = fileReader.read()) != -1) {
                occurances = args[1].toCharArray()[0] == (char)currentChar ? occurances + 1 : occurances;
            }

            fileReader.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {}

        System.out.printf("The letter '%s' occurs %d times in the %s file.", args[1], occurances, args[0]);
    }
}
