package testbench;
import bench.hdd.*;
public class TestHDDWriteSpeed {
    public static void main(String[] args) {
        HDDWriteSpeed bench = new HDDWriteSpeed();
        bench.run("fs", true);
        bench.run("fs", false);
        bench.run("fb", true);
        bench.run("fb", false);
    }
}
