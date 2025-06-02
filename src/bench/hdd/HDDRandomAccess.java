package bench.hdd;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import timing.Timer;
import bench.IBenchmark;

public class HDDRandomAccess implements IBenchmark {

    private final static String PATH = "raf.dat";
    private String result;

    @Override
    public void initialize(Object... params) {
        File tempFile = new File(PATH);
        RandomAccessFile rafFile;
        long fileSizeInBytes = (Long) params[0];

        // temp file for reading and writing
        try {
            rafFile = new RandomAccessFile(tempFile, "rw");
            Random rand = new Random();
            int bufferSize = 4*1024;
            long toWrite = fileSizeInBytes / bufferSize;
            byte[] buffer = new byte[bufferSize];
            long counter = 0;

            while (counter++ < toWrite) {
                rand.nextBytes(buffer);
                rafFile.write(buffer);
            }
            rafFile.close();
            tempFile.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void warmup() {
        try {
            new RandomAccess().randomReadFixedSize(PATH, 4096, 10);
        } catch (IOException e) {
            //ignore errors during warmup
        }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object[]) instead");
    }

    @Override
    public void run(Object ...options) {

        Object[] param = options;
        final int steps = 25000;
        final int runtime = 5000;

        try {
            if (String.valueOf(param[0]).equalsIgnoreCase("r")) {

                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                if (String.valueOf(param[1]).equalsIgnoreCase("fs")) {

                    long timeMs = new RandomAccess().randomReadFixedSize(PATH,
                            bufferSize, steps);
                    result = steps + " random reads in " + timeMs + " ms ["
                            + (steps * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                }
                // read for a fixed time amount and measure time
                else if (String.valueOf(param[1]).equalsIgnoreCase("ft")) {

                    int ios = new RandomAccess().randomReadFixedTime(PATH,
                            bufferSize, runtime);
                    result = ios + " I/Os per second ["
                            + (ios * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                } else
                    throw new UnsupportedOperationException("Read option \""
                            + param[1]
                            + "\" is not implemented");

            }
            // write benchmark
            else if (String.valueOf(param[0]).equalsIgnoreCase("w")) {
               int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                if (String.valueOf(param[1]).equalsIgnoreCase("fs")) {
                    long timeMs = new RandomAccess().randomWriteFixedSize(PATH, bufferSize, steps);
                    result = steps + " random writes in " + timeMs + " ms ["
                            + (steps * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                }
                else if (String.valueOf(param[1]).equalsIgnoreCase("ft")) {
                    int ios = new RandomAccess().randomWriteFixedTime(PATH, bufferSize, runtime);
                    result = ios + " I/Os per second ["
                            + (ios * bufferSize / 1024 / 1024) + " MB, "
                            + 1.0 * (ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                } else
                    throw new UnsupportedOperationException("Write option \"" + param[1] + "\" is not implemented");
            } else
                throw new UnsupportedOperationException("Benchmark option \""
                        + String.valueOf(param[0]) + "\" is not implemented");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clean() {
             File tempFile = new File(PATH);
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Override
    public void cancel() {
        //
    }

    public String getResult() {
        return String.valueOf(result);
    }

    static class RandomAccess {
        private final Random random;

        RandomAccess() {
            random = new Random();
        }


        public long randomReadFixedSize(String filePath, int bufferSize,
                                        int toRead) throws IOException {

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            Timer timer = new Timer();

            timer.start();
            while (counter++ < toRead) {
              long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
                file.seek(pos);
                file.read(bytes);
            }

            file.close();
            return timer.stop() / 1000000; // ns to ms!
        }


        public int randomReadFixedTime(String filePath, int bufferSize,
                                       int millis) throws IOException {

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.getChannel().size();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];

            long now = System.nanoTime();
            long duration = millis * 1_000_000L;
            while ((System.nanoTime()-now) < duration) {
                long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
                file.seek(pos);
                file.read(bytes);

                counter++;
            }

            file.close();
            return counter;
        }
        public long randomWriteFixedSize(String filePath, int bufferSize, int toWrite) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.length();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            random.nextBytes(bytes); // fill buffer with random data
            Timer timer = new Timer();

            timer.start();
            while (counter++ < toWrite) {
                long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
                file.seek(pos);
                file.write(bytes);
            }
            file.close();
            return timer.stop() / 1000000; // ns to ms!
        }

        public int randomWriteFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            long fileSize = file.length();
            int counter = 0;
            byte[] bytes = new byte[bufferSize];
            random.nextBytes(bytes); // fill buffer with random data

            long start = System.nanoTime();
            long duration = millis * 1_000_000L;
            while ((System.nanoTime() - start) < duration) {
                long pos = Math.abs(random.nextLong()) % (fileSize - bufferSize);
                file.seek(pos);
                file.write(bytes);
                counter++;
            }
            file.close();
            return counter;
        }

        public byte[] readFromFile(String filePath, int position, int size)
                throws IOException {

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(position);
            byte[] bytes = new byte[size];
            file.read(bytes);
            file.close();
            return bytes;
        }


        public void writeToFile(String filePath, String data, int position)
                throws IOException {

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(position);
            file.write(data.getBytes());
            file.close();
        }
    }

}
