package JavaBasics_3;

import java.io.FileWriter;
import java.io.IOException;

public class assignment2 {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid Arguments. Specify a file path (Abosolute or relative) and text to write enclosed in quotations");
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(args[0], true);
            fileWriter.append(args[1] + "\n");
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
