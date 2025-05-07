import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int arraySize = 1000000;
        int[] array = ArrayUtils.generateArray(arraySize);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of threads: ");
        int numThreads = scanner.nextInt();

        MinFinderThread.startThreadsAndWait(array, numThreads);
    }
}

