package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestVirtualMemory {
    public static void main(String[] args) {
        VirtualMemoryBenchmark bench = new VirtualMemoryBenchmark();
        ILogger log = new ConsoleLogger();

        // scenario 1: fits in RAM ex.2GB
        log.write("Running Scenario I (Fits in RAM):");
        bench.run(2L * 1024 * 1024 * 1024, 4 * 1024); //2GB,4KB buffer
        log.write(bench.getResult());

        //scenario 2: exceeds RAM ex.16GB
        log.write("\nRunning Scenario II (Triggers Paging):");
        bench.run(16L * 1024 * 1024 * 1024, 4 * 1024); //16GB,4KB buffer
        log.write(bench.getResult());
    }
}
