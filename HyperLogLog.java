import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HyperLogLog {

    private final int p;
    private final int m;
    private final double alpha;
    private final byte[] registers;

    public HyperLogLog(int p) {
        this.p = p;
        this.m = 1 << p;
        this.registers = new byte[m];

        if (m == 16) alpha = 0.673;
        else if (m == 32) alpha = 0.697;
        else if (m == 64) alpha = 0.709;
        else alpha = 0.7213 / (1 + 1.079 / m);
    }

    private long hash(Object value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(value.toString().getBytes());
            return ByteBuffer.wrap(bytes).getLong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(Object value) {

        long x = hash(value);

        // bucket index
        int bucket = (int) (x & (m - 1));

        // remaining bits
        long w = x >>> p;

        int rho;

        if (w == 0) {
            rho = 64 - p + 1;
        } else {
            rho = Long.numberOfLeadingZeros(w) + 1 - p;
        }

        registers[bucket] = (byte) Math.max(registers[bucket], rho);
    }

    public double estimate() {

        double sum = 0.0;

        for (byte r : registers) {
            sum += Math.pow(2.0, -r);
        }

        double estimate = alpha * m * m / sum;

        int zeroRegisters = 0;
        for (byte r : registers) {
            if (r == 0) zeroRegisters++;
        }

        if (estimate <= 2.5 * m && zeroRegisters > 0) {
            estimate = m * Math.log((double) m / zeroRegisters);
        }

        return estimate;
    }

    public void merge(HyperLogLog other) {

        if (this.p != other.p) {
            throw new IllegalArgumentException("Precision values must match");
        }

        for (int i = 0; i < m; i++) {
            registers[i] = (byte) Math.max(registers[i], other.registers[i]);
        }
    }
}


