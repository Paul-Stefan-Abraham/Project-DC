package logging;

public enum TimeUnit {
    Nano, Micro, Milli, Sec;

    public static double convert(long timeNs, TimeUnit unit) {
        return switch (unit) {
            case Nano -> timeNs;
            case Micro -> timeNs / 1_000.0;
            case Milli -> timeNs / 1_000_000.0;
            case Sec -> timeNs / 1_000_000_000.0;
        };
    }
}