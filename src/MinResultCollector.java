public class MinResultCollector {
    private int globalMin = Integer.MAX_VALUE;
    private int globalIndex = -1;
    private int completedThreads = 0;
    private final int totalThreads;

    public MinResultCollector(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    public synchronized void collect(int localMin, int localIndex) {
        if (localMin < globalMin) {
            globalMin = localMin;
            globalIndex = localIndex;
        }
        completedThreads++;
        if (completedThreads == totalThreads) {
            notifyAll();
        }
    }

    public synchronized int[] getResult() {
        while (completedThreads < totalThreads) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return new int[]{globalMin, globalIndex};
    }
}
