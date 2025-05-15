import java.util.Random;

public class ArrayUtils {
    public static int[] generateArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000);
        }
        int negativeIndex = rand.nextInt(size);
        array[negativeIndex] = -rand.nextInt(100) - 1;
        return array;
    }
}
