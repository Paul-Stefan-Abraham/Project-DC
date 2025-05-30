package testbench;

import bench.IBenchmark;
import benchmark.cpu.CPUDigitsOfPi;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestCPUDigitsOfPi {
    public static void main(String[] args) {
        IBenchmark bench = new CPUDigitsOfPi();
        ILogger logger = new ConsoleLogger();
        ITimer timer = new Timer();

        int digits = 500; // how many digits of Pi to compute

        bench.initialize(digits);
        bench.warmup(); // warmup phase

        // Repeat benchmark runs
        for (int i = 0; i < 5; i++) {
            timer.start();
            bench.run();
            long t = timer.stop();
            logger.writeTime("Run " + (i + 1), t, TimeUnit.Milli);
        }

        bench.clean();
        logger.close();
    }
}
