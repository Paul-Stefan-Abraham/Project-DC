package testbench;
import bench.hdd.*;
import bench.DemoBenchmark;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        IBenchmark bench = new HDDWriteSpeed();
        ILogger log = new ConsoleLogger();
        ITimer timer = new Timer();


        // Timed run
        timer.start();
        
        bench.run("fs", true);
        long elapsed = timer.stop();
        log.write("Elapsed time:", elapsed, "ns");
        timer.start();
        bench.run("fs", false);
        elapsed = timer.stop();
        log.write("Elapsed time:", elapsed, "ns");
        timer.start();

        timer.start();
        bench.run("fb", false);
        elapsed = timer.stop();
        log.write("Elapsed time:", elapsed, "ns");

        timer.start();
        bench.run("fb", true);
        elapsed = timer.stop();
        log.write("Elapsed time:", elapsed, "ns");

        bench.clean();
        log.close();
    }
}
