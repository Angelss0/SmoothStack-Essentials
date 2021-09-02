package JavaBasics_4;

public class assignment3 {
    final static int THREAD_COUNT = 2000;
    final static int BUFFER_SIZE = 50;

    public static volatile Integer[] bufferedArray = new Integer[BUFFER_SIZE];
    public static volatile Integer size = 0;

    /**
     * Prints out the contents of the array provided.
     * @param _array
     */
    public static synchronized void drawArrayContents(Integer[] _array) {
        System.out.print("[");
        for (int i = 0; i < size-1; i++) {
            System.out.print(_array[i] + " ");
        }
        
        if (size - 1 >= 0)
            System.out.print(_array[size-1]);

        System.out.println("]");
    }

    /**
     * Takes in a buffered array and produces ints of 1s.
     * @param bufferedArray
     */
    public static void producer(Integer[] _array) {
        synchronized(_array) {
            synchronized(size) {
                System.out.print("Producing! ");
                _array[size] = 1;
                size = (size + 1) > BUFFER_SIZE-1 ? BUFFER_SIZE-1 : size + 1;
                System.out.println("Current Size: " + size);
                drawArrayContents(_array);
            }
        }
    }

    /**
     * Takes in a buffered array and consumes ints turns them to 0s.
     * @param bufferedArray
     */
    public static void consumer(Integer[] _array) {
        synchronized(_array) {
            synchronized(size) {
                System.out.print("Consuming! ");
                _array[size] = 0;
                size = (size - 1) < 0 ? 0 : size - 1;
                System.out.println("Current Size: " + size);
                drawArrayContents(_array);
            }
        }
    }

    public static void main(String[] args) {
        Thread[] producers = new Thread[THREAD_COUNT];
        Thread[] consumers = new Thread[THREAD_COUNT];

        for (int i = 0; i < THREAD_COUNT; i++) {
            producers[i] = new Thread(() -> producer(bufferedArray));
            producers[i].start();

            consumers[i] = new Thread(() -> consumer(bufferedArray));
            consumers[i].start();
        }

        try {
            for (int k = 0; k < THREAD_COUNT; k++) {
                producers[k].join();
                consumers[k].join();
            }
        } catch (Exception e) {}
    }
}
