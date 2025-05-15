public class MinFinderExecutor {
    private final int[] array;
    private final int numThreads;
    private final MinResultCollector collector;

    public MinFinderExecutor(int[] array, int numThreads) {
        this.array = array;
        this.numThreads = numThreads;
        this.collector = new MinResultCollector(numThreads);
    }

    public int[] execute() {
        int chunkSize = array.length / numThreads;
        Thread[] threads = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? array.length : start + chunkSize;

            MinFinderThread worker = new MinFinderThread(array, start, end, collector);
            threads[i] = new Thread(worker);
            threads[i].start();
        }

        return collector.getResult();
    }
}
