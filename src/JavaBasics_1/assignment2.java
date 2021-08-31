package JavaBasics_1;

import java.util.Random;
import java.util.Scanner;

class assignment2 {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int randomNum = random.nextInt(101);
        int userNum = -11;
        int errorCount = 0;

        do {
            if (errorCount >= 5) {
                System.out.printf("Sorry\n%d", randomNum);
                scanner.close();
                return;
            } else if (errorCount > 0) {
                System.out.println("Try Again!");
            }

            System.out.print("Guess a number between 0-100: ");
            try {
                userNum = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine();
            }

            errorCount++;
        } while (userNum > (randomNum + 10) || userNum < (randomNum - 10));

        System.out.printf("Guessed Within +- 10 of: %d!\n", randomNum);
        scanner.close();
    }
}
