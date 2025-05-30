package benchmark.cpu;

import bench.IBenchmark;

import java.math.BigDecimal;
import java.math.MathContext;

public class CPUDigitsOfPi implements IBenchmark {
    private int digits;
    private boolean running = true;

    @Override
    public void initialize(Object... params) {
        if (params.length < 1 || !(params[0] instanceof Integer))
            throw new IllegalArgumentException("Expected integer number of digits");

        digits = (int) params[0];
        running = true;
    }

    @Override
    public void run() {
        computePi(digits);
    }

    @Override
    public void run(Object... options) {
        run();
    }

    @Override
    public void warmup() {
        computePi(200); // small warmup run (200 digits)
    }

    @Override
    public void clean() {
        // Nothing to clean here
    }

    @Override
    public void cancel() {
        running = false;
    }

    private BigDecimal computePi(int digits) {
        MathContext mc = new MathContext(digits + 5); // a bit extra for rounding
        BigDecimal pi = BigDecimal.ZERO;
        BigDecimal sixteen = new BigDecimal(16);
        BigDecimal one = BigDecimal.ONE;

        for (int k = 0; k < digits && running; k++) {
            BigDecimal kBD = BigDecimal.valueOf(k);
            BigDecimal term = BigDecimal.ZERO;

            term = term.add(fraction(4, 8 * k + 1, mc));
            term = term.subtract(fraction(2, 8 * k + 4, mc));
            term = term.subtract(fraction(1, 8 * k + 5, mc));
            term = term.subtract(fraction(1, 8 * k + 6, mc));

            term = term.divide(BigDecimal.valueOf(16).pow(k, mc), mc);
            pi = pi.add(term, mc);
        }

        return pi.round(new MathContext(digits));
    }

    private BigDecimal fraction(int numerator, int denominator, MathContext mc) {
        return BigDecimal.valueOf(numerator).divide(BigDecimal.valueOf(denominator), mc);
    }
}
