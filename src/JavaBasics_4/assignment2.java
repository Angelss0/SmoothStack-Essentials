package JavaBasics_4;

public class assignment2 {
    public static Integer resource1 = 15;
    public static Integer resource2 = 17;

    /**
     * Function that causes deadlock.
     * @param r1 resource 1 to hold a lock on.
     * @param r2 resource 2 to hold a lock on.
     */
    public static void deadlocker(Integer r1, Integer r2) {
        try {
            synchronized (r2) {
                System.out.printf("T%d acquired R%d!\n", Thread.currentThread().getId(), r2);

                synchronized (r1) {
                    System.out.printf("T%d acquired R%d!\n", Thread.currentThread().getId(), r1);

                    r1++;
                    r2++;
                }
            }
            System.out.println("Released r1 and r2!");
        } catch (Exception e) {}

        System.out.printf("Finished T%d!\n", Thread.currentThread().getId());
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> deadlocker(resource1, resource2));
        Thread thread2 = new Thread(() -> deadlocker(resource2, resource1));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (Exception e) {}

        System.out.printf("End of program! Results:\nr1: %d, r2: %d\n", resource1, resource2);
    }
}
