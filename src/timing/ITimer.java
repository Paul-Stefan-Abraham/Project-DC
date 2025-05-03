package timing;

/**

 Interface for a stopwatch-style timer.*/
public interface ITimer {
    void start();
    long stop();
    void resume();
    long pause();
}