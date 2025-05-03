package timing;

public class Timer implements ITimer {
    private long startTime = 0;
    private long pauseStart = 0;
    private long accumulatedTime = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;

    @Override
    public void start() {
        startTime = System.nanoTime();
        accumulatedTime = 0;
        isRunning = true;
        isPaused = false;
    }

    @Override
    public long stop() {
        if (!isRunning) return -1;

        long endTime = isPaused ? pauseStart : System.nanoTime();
        long totalTime = accumulatedTime + (endTime - startTime);

        isRunning = false;
        isPaused = false;
        return totalTime;
    }

    @Override
    public void resume() {
        if (!isPaused) return;
        long now = System.nanoTime();
        // Update startTime to reflect resumed time window
        startTime = now;
        isPaused = false;
    }

    @Override
    public long pause() {
        if (!isRunning || isPaused) return -1;

        pauseStart = System.nanoTime();
        long delta = pauseStart - startTime;
        accumulatedTime += delta;
        isPaused = true;
        return delta;
    }
}