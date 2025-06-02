package testbench;

import bench.DummyBenchmark;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.*;

public class BenchmarkTest {
    public static void main(String[] args) {
        IBenchmark benchmark = new DummyBenchmark();
        ILogger logger = new ConsoleLogger();
        ITimer timer = new Timer();

        benchmark.initialize(100000);

        timer.start();
        benchmark.run();
        long elapsed = timer.stop();

        logger.write("Elapsed time:", elapsed, "ns");

        benchmark.clean();
        logger.close();
    }
}