public class MinFinderThread implements Runnable {
    private final int[] array;
    private final int start;
    private final int end;
    private final MinResultCollector collector;

    public MinFinderThread(int[] array, int start, int end, MinResultCollector collector) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.collector = collector;
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
        collector.collect(localMin, localIndex);
    }
}
