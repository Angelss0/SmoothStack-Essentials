package JavaBasics_4;

public class assignment1 {
    public static void main(String[] args) {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        System.out.printf("Singleton1 hash: %d\nSingleton2 hash: %d\n", singleton1.hashCode(), singleton2.hashCode());
    }
}
