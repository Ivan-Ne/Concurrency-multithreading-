import java.util.ArrayList;
import java.util.List;

public class Main {

    // Constant variable. Size of array for 10 000 000 elements.
    private static final int SIZE = 10_000_000;

    // Constant variable. Size of array for 5 000 000 elements.
    private static final int HALF = SIZE / 2;

    public static void main(String[] args) {

        // Calling method withConcurrency
        withConcurrency();
        // Calling method withoutConcurrency
        withoutConcurrency();

    }

    // Method withoutConcurrency calculates values of elements from array.
    // Also calculates needed time for calculation values of 10 000 000 elements.
    private static void withoutConcurrency(){
        float [] array = new float[SIZE];
        for (int i = 0; i < SIZE; i++){
            array[i] = 1f;
        }
        long before = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++){
            float f = (float) i;
            array[i] = (float) (array[i] * Math.sin(0.2f + f/5)*Math.cos(0.2f + f/5)*Math.cos(0.4f + f/2));
        }
        long after = System.currentTimeMillis();
        System.out.println("Without concurrency: " + (after - before));
    }

    // Method withConcurrency calculates values of elements from two arrays.
    // Also calculates needed time for calculation values of 10 000 000 elements in two threads.
    private static void withConcurrency(){
        float [] array = new float[SIZE];
        for (int i = 0; i < SIZE; i++){
            array[i] = 1f;
        }
        long before = System.currentTimeMillis();
        float[] firstHALF = new float[HALF];
        float[] secondHALF = new float[HALF];
        System.arraycopy(array, 0, firstHALF, 0, HALF);
        System.arraycopy(array, HALF, secondHALF, 0, HALF);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++){
                    float f = (float) i;
                    firstHALF[i] = (float) (firstHALF[i] * Math.sin(0.2f + f/5)*Math.cos(0.2f + f/5)*Math.cos(0.4f + f/2));
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < HALF; i++){
                    float f = (float) (i + HALF);
                    secondHALF[i] = (float) (secondHALF[i] * Math.sin(0.2f + f/5)*Math.cos(0.2f + f/5)*Math.cos(0.4f + f/2));
                }
            }
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(firstHALF, 0, array, 0, HALF);
        System.arraycopy(secondHALF, 0, array, HALF, HALF);
        long after = System.currentTimeMillis();
        System.out.println("With concurrency: " + (after - before));
    }
}
