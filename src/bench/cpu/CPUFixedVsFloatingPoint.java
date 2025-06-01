package bench.cpu;

import bench.IBenchmark;

public class CPUFixedVsFloatingPoint implements IBenchmark {
    private NumberRepresentation mode;
    private int size;

    @Override
    public void initialize(Object... params) {
        if (params.length >= 2) {
            mode = (NumberRepresentation) params[0];
            size = (int) params[1];
        }
    }

    @Override
    public void run() {
        double result = 0;

        if (mode == NumberRepresentation.FLOATING) {
            for (int i = 1; i < size; i++) {
                result += i / 256.0; // floating-point division
            }
        } else {
            for (int i = 1; i < size; i++) {
                result += i >> 8; // fixed-point: i / 256 = i >> 8
            }
        }
    }

    @Override
    public void warmup() {
        initialize(NumberRepresentation.FIXED, 1000);
        run();
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void clean() {}

    @Override
    public void cancel() {}
}
