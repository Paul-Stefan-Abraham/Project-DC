package testbench;
import bench.cpu.HDDRandomAccess;
import bench.DemoBenchmark;
import bench.IBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestHDDRandomAccess {
    public static void main(String[] args) {
        IBenchmark bench = new HDDRandomAccess();
        ILogger log = new ConsoleLogger();
        ITimer timer = new Timer();
        
        long gigaBytes = 1024*1024*1024;
        long[]sizes={512, 4*1024, 64*1024, 1024*1024};
        bench.initialize(gigaBytes); ///1GB

        for(long size:sizes){
            timer.start();
            bench.run("r","fs", size);
            long elapsed = timer.stop();
            log.write("Elapsed time for reading ft:", elapsed, "ns with "+size+" bytes");
            timer.start();
            bench.run("w", "fs", size);
            elapsed = timer.stop();
            log.write("Elapsed time for writing fs", elapsed, "ns with "+size+" bytes");
            timer.start();
            bench.run("r", "ft", size);
            elapsed = timer.stop();
            log.write("Elapsed time for reading ft", elapsed, "ns with "+size+" bytes");
            timer.start();
            bench.run("w", "ft", size);
            elapsed = timer.stop();
            log.write("Elapsed time for writing ft:", elapsed, "ns with "+size+" bytes");
        }
        bench.clean();
        log.close();

        System.out.println("Test completed successfully.");
    }
}