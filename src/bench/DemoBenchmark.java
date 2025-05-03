package bench;

import java.util.Random;

public class DemoBenchmark implements IBenchmark {
    private int[] array;
    private int n;
    private boolean running = true;

    @Override
    public void initialize(Object... params) {
        if (params.length < 1 || !(params[0] instanceof Integer))
            throw new IllegalArgumentException("Expected integer size for array");

        n = (int) params[0];
        array = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
            array[i] = rand.nextInt();
    }

    @Override
    public void run() {
        if (array == null) return;

        // Bubble sort
        running = true;
        for (int i = 0; i < array.length - 1 && running; i++) {
            for (int j = 0; j < array.length - i - 1 && running; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                }
            }
        }
    }

    @Override
    public void run(Object... params) {
        run();
    }

    @Override
    public void clean() {
        array = null;
    }

    @Override
    public void cancel() {
        running = false;
    }
}