import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the size of the array: ");
        int arraySize = scanner.nextInt();

        System.out.print("Enter the number of threads: ");
        int numThreads = scanner.nextInt();

        int[] array = ArrayUtils.generateArray(arraySize);

        MinFinderExecutor executor = new MinFinderExecutor(array, numThreads);
        int[] result = executor.execute();

        System.out.println("Global Min: " + result[0] + ", Index: " + result[1]);
    }
}
