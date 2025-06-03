package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

public class TestVirtualMemory {
    static VirtualMemoryBenchmark bench = new VirtualMemoryBenchmark();
    static ILogger log = new ConsoleLogger();
    public static void main(String[] args) {

        // scenario 1: fits in RAM ex.2GB
        scenario1();

        //scenario 2: exceeds RAM ex.16GB
        scenario2();
    }

    public static void scenario1(){
        log.write("Running Scenario I (Fits in RAM):");
        bench.run(2L * 1024 * 1024 * 1024, 4 * 1024); //2GB,4KB buffer
        log.write(bench.getResult());

    }

    public static void scenario2(){
        log.write("\nRunning Scenario II (Triggers Paging):");
        bench.run(16L * 1024 * 1024 * 1024, 4 * 1024); //16GB,4KB buffer
        log.write(bench.getResult());

    }
}

