package JavaBasics_1;

class assignment1 {
    
    /**
     * Prints out a sequence of patternChar characters.
     * @param num number of characters to print
     * @param spaces number of spaces before the character to print
     * @param patternChar type of character to print
     */
    public static void printChar(int num, int spaces, char patternChar) {
        for (int i = 0; i < spaces; i++) System.out.print(" ");
        for (int j = 0; j < num; j++) System.out.print(patternChar);
        System.out.println("");
    }

    public static void main(String[] args) {
        for (int k = 0; k < 4; k++) printChar(k+1, 0, '*');
        printChar(9, 0, '.');
        System.out.println("");

        printChar(10, 0, '.');
        for (int k = 0; k < 4; k++) printChar(4-k, 0, '*');
        System.out.println("");

        for (int k = 0; k < 4; k++) printChar(2*k+1, 1 + (4-k), '*');
        printChar(11, 0, '.');
        System.out.println("");

        printChar(12, 0, '.');
        for (int k = 0; k < 4; k++) printChar(2*(4-k)-1, k+2, '*');
        System.out.println("");
    }
}