package testbench;

import bench.DemoBenchmark;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestDemoBench {
    public static void main(String[] args) {
        IBenchmark bench = new DemoBenchmark();
        ILogger logger = new ConsoleLogger();
        ITimer timer = new Timer();

        int sleepMs = 100;
        bench.initialize(sleepMs);

        try {
            timer.start();
            Thread.sleep(sleepMs);
            long t = timer.stop();

            double offset = 100.0 * (t - sleepMs * 1_000_000) / (sleepMs * 1_000_000);
            logger.write("Measured time:", t, "ns");
            logger.write(String.format("Offset: %.2f%%", offset));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bench.initialize(500);

        timer.start();

        for (int i = 0; i < 5; i++) {
            timer.resume();
            bench.run();
            long t = timer.pause();
            logger.writeTime("Step " + (i + 1), t, TimeUnit.Milli);
        }

        long total = timer.stop();
        logger.writeTime("Total time", total, TimeUnit.Milli);

        logger.close();
    }
}