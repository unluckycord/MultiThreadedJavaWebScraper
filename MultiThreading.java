public class MultiThreading {
    public static void CreatesThreads(int AmountOfThreads) {
        // Create an array to hold the threads
        Thread[] threads = new Thread[AmountOfThreads];

        // Create and start the threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyRunnable("Thread " + (i + 1)));
            threads[i].start();
        }

        // Wait for all the threads to finish
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished execution.");
    }

    static class MyRunnable implements Runnable {
        private final String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Thread " + name + " started.");

            try {
                // Simulate some work
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread " + name + " finished.");
        }
    }
}
