package testbench;

import bench.IBenchmark;
import bench.cpu.CPURecursionLoopUnrolling;
import logging.ConsoleLogger;
import logging.ILogger;
import timing.ITimer;
import timing.Timer;

public class TestCPURecursionLoopUnrolling {
    public static void main(String[] args) {
        IBenchmark bench = new CPURecursionLoopUnrolling();
        ILogger log = new ConsoleLogger();
        ITimer timer = new Timer();

        int size = 2_000_000;

        // No unrolling
        bench.initialize(size);
        timer.start();
        bench.run(false);
        long timeNoUnroll = timer.stop();

        // With unrolling
        for (int unroll = 1; unroll <= 15; unroll += 5) {
            bench.initialize(size);
            timer.start();
            bench.run(true, unroll);
            long timeUnroll = timer.stop();

            // Example custom score: (calls or primes reached) / time * unroll factor
            double score = (double) size / (timeUnroll / 1e6) * unroll;
            log.write("Custom Score (unroll " + unroll + "):", String.format("%.2f", score));
        }

        log.close();
    }
}
