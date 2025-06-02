package bench.cpu;

import bench.IBenchmark;

import java.util.Random;

public class CPUFixedPoint implements IBenchmark {
    private int size;
    private int[] num, res, a, b, c;

    @Override
    public void initialize(Object... params) {
        size = (int) params[0];
        Random rand = new Random();

        num = new int[]{0, 1, 2, 3};
        res = new int[size];
        a = new int[size];
        b = new int[size];
        c = new int[size];

        for (int i = 0; i < size; i++) {
            a[i] = rand.nextInt(4);
            b[i] = rand.nextInt(size);
        }
    }

    @Override
    public void warmup() {
        initialize(1000);
        run();
    }

    @Override
    public void run() {
        arithmeticOps();
        branchingOps();
        arrayOps();
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void clean() {}

    @Override
    public void cancel() {}

    private void arithmeticOps() {
        int j = 1, k = 2, l = 3;
        for (int i = 3; i < size; i++) {
            j = num[1] * (k - j) * (l - k);
            k = num[3] * k - (l - j) * k;
            l = (l - k) * (num[2] + j);
            res[l % size] = j + k + l;
            res[k % size] = j * k * l;
        }
    }

    private void branchingOps() {
        int j = 1;
        for (int i = 0; i < size; i++) {
            if (j == 1) j = num[2];
            else j = num[3];

            if (j > 2) j = num[0];
            else j = num[1];

            if (j < 1) j = num[1];
            else j = num[0];
        }
    }

    private void arrayOps() {
        for (int i = 0; i < size; i++) {
            int index = b[i] % size;
            c[i] = a[index];
        }
    }

    public int getOperationCount() {
        //estimate of op from doc
        return 29;
    }
}
