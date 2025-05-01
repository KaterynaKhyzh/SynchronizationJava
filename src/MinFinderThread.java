public class MinFinderThread implements Runnable {
    private int[] array;
    private int start, end;
    private static int globalMin = Integer.MAX_VALUE;
    private static int globalIndex = -1;
    private static int completedThreads = 0;
    private static int totalThreads = 0;
    private static final Object locker = new Object();

    public MinFinderThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int localMin = Integer.MAX_VALUE;
        int localIndex = -1;

        for (int i = start; i < end; i++) {
            if (array[i] < localMin) {
                localMin = array[i];
                localIndex = i;
            }
        }

        synchronized (locker) {
            if (localMin < globalMin) {
                globalMin = localMin;
                globalIndex = localIndex;
            }
            completedThreads++;
        }
    }

    public static int getGlobalMin() {
        return globalMin;
    }

    public static int getGlobalIndex() {
        return globalIndex;
    }

    public static int getCompletedThreads() {
        return completedThreads;
    }

    public static void setTotalThreads(int totalThreads) {
        MinFinderThread.totalThreads = totalThreads;
    }

    public static boolean allThreadsCompleted() {
        synchronized (locker) {
            return completedThreads == totalThreads;
        }
    }
}
