package testbench;

import benchmark.cpu.CPUFixedPoint;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestCPUFixedPoint {
    public static void main(String[] args) {
        int size = 1_000_000;

        CPUFixedPoint bench = new CPUFixedPoint();
        ILogger log = new ConsoleLogger();
        ITimer timer = new Timer();

        bench.initialize(size);
        bench.warmup();

        timer.start();
        bench.run();
        long time = timer.stop(); // in nanoseconds

        int ops = bench.getOperationCount() * size;
        double mops = ops / (time / 1e6);

        log.writeTime("Total time", time, TimeUnit.Milli);
        log.write("Estimated MOPS:", String.format("%.2f", mops));
        log.close();
    }
}
