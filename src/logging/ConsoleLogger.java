package logging;

public class ConsoleLogger implements ILogger {

    @Override
    public void write(long value) {
        System.out.println(value);
    }

    @Override
    public void write(String value) {
        System.out.println(value);
    }

    @Override
    public void write(Object... values) {
        for (Object val : values)
            System.out.print(val + " ");
        System.out.println();
    }

    @Override
    public void writeTime(String label, long time, TimeUnit unit) {
        double converted = TimeUnit.convert(time, unit);
        System.out.printf("%s %.3f %s\n", label, converted, unit.name());
    }

    @Override
    public void close() {}
}