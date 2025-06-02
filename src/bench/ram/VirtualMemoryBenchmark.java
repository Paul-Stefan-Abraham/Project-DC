package bench.ram;

import java.io.IOException;
import java.util.Random;

import timing.Timer;
import bench.IBenchmark;

/**
 * Maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower speeds).
 */
public class VirtualMemoryBenchmark implements IBenchmark {

    private String result = "";
    private MemoryMapper core;

    @Override
    public void initialize(Object... params) {
        // Initialization will be handled in run
    }


    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object[]) instead");
    }

    @Override
    public void run(Object... options) {
        // expected: {fileSize, bufferSize}
        long fileSize = Long.parseLong(options[0].toString());
        int bufferSize = Integer.parseInt(options[1].toString());

        try {
            core = new MemoryMapper("core_mem_", fileSize);
            byte[] buffer = new byte[bufferSize];
            Random rand = new Random();

            Timer timer = new Timer();

            // --- WRITE to VM ---
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                rand.nextBytes(buffer); // 1. generate random content
                core.put(i, buffer);    // 2. write to memory mapper
            }
            double writeTimeSeconds = timer.stop() / 1e9; // convert to seconds
            double writeSpeedMBps = (fileSize / 1024.0 / 1024.0) / writeTimeSeconds;
            result = String.format("\nWrote %d MB to virtual memory at %.2f MB/s",
                    fileSize / 1024 / 1024, writeSpeedMBps);

            // --- READ from VM ---
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                core.get(i, bufferSize); // 5. read from memory mapper
            }
            double readTimeSeconds = timer.stop() / 1e9;
            double readSpeedMBps = (fileSize / 1024.0 / 1024.0) / readTimeSeconds;

            result += String.format("\nRead %d MB from virtual memory at %.2f MB/s",
                    fileSize / 1024 / 1024, readSpeedMBps);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (core != null) {
                core.purge();
            }
        }
    }

    @Override
    public void clean() {
        if (core != null) {
            core.purge();
        }
    }

    @Override
    public void cancel() {

    }

    @Override
    public void warmup() {

    }

    public String getResult() {
        return result;
    }
}
