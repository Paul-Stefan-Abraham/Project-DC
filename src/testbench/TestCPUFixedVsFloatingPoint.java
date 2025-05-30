package testbench;

import bench.IBenchmark;
import benchmark.cpu.CPUFixedVsFloatingPoint;
import benchmark.cpu.NumberRepresentation;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestCPUFixedVsFloatingPoint {
    public static void main(String[] args) {
        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();

        for (NumberRepresentation rep : NumberRepresentation.values()) {
            IBenchmark bench = new CPUFixedVsFloatingPoint();
            bench.initialize(rep, 100_000_000);
            bench.warmup();

            timer.start();
            bench.run();
            long time = timer.stop();

            log.write(rep + " time:");
            log.writeTime("Total", time, TimeUnit.Milli);
            log.write();
        }

        log.close();
    }
}
