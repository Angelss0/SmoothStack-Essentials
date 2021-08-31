package JavaBasics_3;

import java.io.File;

public class assignment1 {
    /**
     * Recursive Function that retrieves all of the files in a directory.
     * @param path
     */
    public static void getFilesInDirectory(String path) {
        File files = new File(path);
        if (files.isDirectory()) {
            for (String file : files.list()) {
                System.out.println(path + "/" + file);
                getFilesInDirectory(path + "/" + file);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid Arguments. Specify only a path. (Abosolute or relative)");
            return;
        }

        getFilesInDirectory(args[0]);
    }
}
