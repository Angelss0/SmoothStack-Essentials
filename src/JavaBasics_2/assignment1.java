package JavaBasics_2;

class assignment1 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Program usage: java -cp .. JavaBasics_2.assignment1 <num1> <num2> ... <numN>");
            return;
        }

        int result = Integer.parseInt(args[0]);

        for (int i = 1; i < args.length; i++)
            result += Integer.parseInt(args[i]);

        System.out.println(result);
    }
}