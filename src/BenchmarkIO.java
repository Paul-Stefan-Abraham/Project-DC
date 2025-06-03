import logging.ConsoleLogger;
import logging.ILogger;
import testbench.*;

import java.util.Scanner;

private static final Scanner scanner = new Scanner(System.in);
private static final ILogger log = new ConsoleLogger();

public static void main(String[] args) {

    printMenu();

    while (true) {

        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                log.write("\n~~~~~~~~~~~~~~Demo Benchmark~~~~~~~~~~~~");
                TestDemoBench.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "2":
                log.write("\n~~~~~~~~~~~~~Dummy Benchmark~~~~~~~~~~~~");
                BenchmarkTest.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "3":
                log.write("\n~~~~~~~CPU digits of Pi Benchmark~~~~~~~");
                TestCPUDigitsOfPi.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "4":
                log.write("\n~~~~~~~~CPU Fixed Point Benchmark~~~~~~~");
                TestCPUFixedPoint.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "5":
                log.write("\n~~~~~CPU Fixed Point vs Floating Point Benchmark~~~~~");
                TestCPUFixedVsFloatingPoint.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "6":
                log.write("\n~~~~~CPU Recursion Loop Unrolling Benchmark~~~~~");
                TestCPURecursionLoopUnrolling.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "7":
                log.write("\n~~~~~~HDD Random Access Benchmark~~~~~~");
                TestHDDRandomAccess.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "8":
                log.write("\n~~~~~~HDD Write Speed Benchmark~~~~~~");
                TestHDDWriteSpeed.main(args);
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "9":
                log.write("\n~~~~~RAM Benchmark (VM Scenario1)~~~~~");
                TestVirtualMemory.scenario1();
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "10":
                log.write("\n~~~~~RAM Benchmark (VM Scenario2)~~~~~");
                TestVirtualMemory.scenario2();
                log.write("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            case "11":
                printMenu();
                break;
            case "12":
                log.write("Exiting. Goodbye!");
                return;
            default:
                log.write("Invalid choice. Please try again.");
        }
    }
}


private static void printMenu() {
    log.write("\n=== BENCHMARK MENU ===");
    log.write("1. Demo Benchmark");
    log.write("2. Dummy Benchmark");
    log.write("3. CPU digits of Pi Benchmark");
    log.write("4. CPU Fixed Point Benchmark");
    log.write("5. CPU Fixed Point vs Floating Point Benchmark");
    log.write("6. CPU Recursion Loop Unrolling Benchmark");
    log.write("7. HDD Random Access Benchmark");
    log.write("8. HDD Write Speed Benchmark");
    log.write("9. RAM Benchmark (VM Scenario1)");
    log.write("10. RAM Benchmark (VM Scenario2)");
    log.write("11. Display Menu again");
    log.write("12. Exit");
    log.write("Choose an option: ");
}
