package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {
    private BufferedWriter writer;

    public FileLogger(String filename) {
        try {
            writer = new BufferedWriter(new FileWriter(filename, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(long value) {
        write(Long.toString(value));
    }

    @Override
    public void write(String value) {
        try {
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Object... values) {
        try {
            for (Object val : values) {
                writer.write(val.toString() + " ");
            }
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTime(String label, long time, TimeUnit unit) {
        double converted = TimeUnit.convert(time, unit);
        try {
            writer.write(String.format("%s %.3f %s\n", label, converted, unit.name()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            if (writer != null)
                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}