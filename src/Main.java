import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int arraySize = 1000000;
        int[] array = ArrayUtils.generateArray(arraySize);

        System.out.print("Enter the number of threads: ");
        Scanner scanner = new Scanner(System.in);
        int numThreads = scanner.nextInt();

        MinFinderThread.setTotalThreads(numThreads);

        int chunkSize = arraySize / numThreads;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? arraySize : start + chunkSize;

            MinFinderThread worker = new MinFinderThread(array, start, end);
            threads[i] = new Thread(worker);
            threads[i].start();
        }

        while (!MinFinderThread.allThreadsCompleted()) {
            try {
                Thread.sleep(10); // Sleep to prevent active spinning
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Global Min: " + MinFinderThread.getGlobalMin() + ", Index: " + MinFinderThread.getGlobalIndex());
    }
}
