class MinFinderThread implements Runnable {
    private int[] array;
    private int start, end;
    private int localMin = Integer.MAX_VALUE;
    private int localIndex = -1;
    private static int globalMin = Integer.MAX_VALUE;
    private static int globalIndex = -1;
    private static final Object globalMonitor = new Object();
    private static int completedThreads = 0;

    public MinFinderThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            if (array[i] < localMin) {
                localMin = array[i];
                localIndex = i;
            }
        }

        synchronized (globalMonitor) {
            if (localMin < globalMin) {
                globalMin = localMin;
                globalIndex = localIndex;
            }
            completedThreads++;
            globalMonitor.notifyAll();
        }
    }

    public static void startThreadsAndWait(int[] array, int numThreads) {
        int arraySize = array.length;
        int chunkSize = arraySize / numThreads;
        Thread[] threads = new Thread[numThreads];

        globalMin = Integer.MAX_VALUE;
        globalIndex = -1;
        completedThreads = 0;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? arraySize : start + chunkSize;
            threads[i] = new Thread(new MinFinderThread(array, start, end));
            threads[i].start();
        }

        synchronized (globalMonitor) {
            while (completedThreads < numThreads) {
                try {
                    globalMonitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            System.out.println("Global Min: " + globalMin + ", Index: " + globalIndex);
        }
    }
}
