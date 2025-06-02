package bench;

import java.util.Random;

public class DemoBenchmark implements IBenchmark {
    private int[] array;
    private boolean running = true;

    @Override
    public void initialize(Object... params) {
        int n = (int) params[0];
        array = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
            array[i] = rand.nextInt();
        running = true;
    }

    @Override
    public void run() {
        if (array == null) return;
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
    public void warmup() {//warmup using a small array
        int[] warmArray = new int[100];
        Random rand = new Random();
        for (int i = 0; i < warmArray.length; i++)
            warmArray[i] = rand.nextInt();

        for (int i = 0; i < warmArray.length - 1; i++) {
            for (int j = 0; j < warmArray.length - i - 1; j++) {
                if (warmArray[j] > warmArray[j + 1]) {
                    int tmp = warmArray[j];
                    warmArray[j] = warmArray[j + 1];
                    warmArray[j + 1] = tmp;
                }
            }
        }
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
