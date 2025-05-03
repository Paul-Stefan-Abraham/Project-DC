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
            double dummy = Math.sqrt(i); // Simulate workload
        }
    }

    @Override
    public void run(Object... params) {
        run();
    }

    @Override
    public void clean() {
        System.gc(); // Optional cleanup
    }

    @Override
    public void cancel() {
        System.out.println("Benchmark canceled.");
    }
}