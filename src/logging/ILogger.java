package logging;

public interface ILogger {
    void write(long value);
    void write(String value);
    void write(Object... values);
    void writeTime(String label, long time, TimeUnit unit);
    void close();
}