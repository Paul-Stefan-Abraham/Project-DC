package bench;

public class DummyBenchmark implements IBenchmark {
    private int runs;

    @Override
    public void initialize(Object... params) {
        runs = (int) params[0];
    }

    @Override
    public void run() {
        for (int i = 0; i < runs; i++) {
            double dummy = Math.sqrt(i); // Simulate CPU load
        }
    }

    @Override
    public void run(Object... params) {
        run();
    }

    @Override
    public void warmup() {
        // Run a small version of the benchmark to warm up JVM
        for (int i = 0; i < 1000; i++) {
            double dummy = Math.sqrt(i);
        }
    }

    @Override
    public void clean() {
        System.gc();
    }

    @Override
    public void cancel() {
        System.out.println("Benchmark canceled.");
    }
}
