package JavaBasics_2;

import java.util.Random;

class assignment2 {

    /**
     * Looks at a 2D array and prints out the value and position of the biggest value.
     * @param arrayVal
     * @param n
     * @param m
     */
    public static void getMaxNumber(int[][] arrayVal, int n, int m) {
        int[] position = new int[2];
        int maxValue = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (arrayVal[i][j] > maxValue) {
                    position[0] = j;
                    position[1] = i;
                    maxValue = arrayVal[i][j];
                }
            }
        }

        System.out.printf("Value: %d, Pos: [%d, %d]", maxValue, position[0], position[1]);
    }

    /**
     * Takes two arguments and produces an int array of [n, m] size.
     * @param n
     * @param m
     * @return
     */
    public static int[][] generateRandomList(int n, int m) {
        Random random = new Random();
        int randomArray[][] = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                randomArray[i][j] = random.nextInt(1000);
                System.out.print(randomArray[i][j] + " ");
            }
            System.out.println("");
        }

        return randomArray;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong arguments provided. Provide two numbers defining the size of a 2d array.");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        int[][] randomArray = generateRandomList(n, m);
        getMaxNumber(randomArray, n, m);
    }
}
