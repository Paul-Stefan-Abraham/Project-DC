package benchmark.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {
    private int size;

    @Override
    public void initialize(Object... params) {
        size = (int) params[0];
    }

    @Override
    public void run() {
        run(false); // default behavior without unrolling
    }

    @Override
    public void run(Object... params) {
        boolean unroll = (boolean) params[0];

        if (!unroll) {
            try {
                long start = System.nanoTime();
                recursive(1, size, 0);
                long end = System.nanoTime();
                printTime(end - start);
            } catch (StackOverflowError e) {
                System.out.println("StackOverflow occurred.");
            }
        } else {
            int unrollFactor = (int) params[1];
            try {
                long start = System.nanoTime();
                recursiveUnrolled(1, unrollFactor, size, 0);
                long end = System.nanoTime();
                printTime(end - start);
            } catch (StackOverflowError e) {
                System.out.println("StackOverflow occurred.");
            }
        }
    }

    private void printTime(long ns) {
        System.out.printf("Finished in %.4f Milli%n", ns / 1_000_000.0);
    }

    private long recursive(long start, long size, int counter) {
        try {
            if (start > size) return 0;
            if (isPrime(start)) {
                // simulate a use of the prime
            }
            return recursive(start + 1, size, counter + 1);
        } catch (StackOverflowError e) {
            System.out.printf("Reached nr %d/%d after %d calls.%n", start, size, counter);
            return 0;
        }
    }

    private long recursiveUnrolled(long start, int unrollLevel, int size, int counter) {
        try {
            for (int i = 0; i < unrollLevel && start <= size; i++, start++) {
                if (isPrime(start)) {
                    // simulate a use of the prime
                }
            }
            if (start > size) return 0;
            return recursiveUnrolled(start, unrollLevel, size, counter + 1);
        } catch (StackOverflowError e) {
            System.out.printf("Reached nr %d/%d at %d levels after %d calls%n", start, size, unrollLevel, counter);
            return 0;
        }
    }

    private boolean isPrime(long x) {
        if (x <= 2) return true;
        for (long i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) return false;
        }
        return true;
    }

    @Override
    public void warmup() {
        // optional small run to warm up JVM
        initialize(10000);
        run(false);
    }

    @Override
    public void clean() {}
    @Override
    public void cancel() {}
}
