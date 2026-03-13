public class Main {

    public static void main(String[] args) {

        HyperLogLog hll = new HyperLogLog(10);

        int n = 100000;

        for (int i = 0; i < n; i++) {
            hll.add(i);
        }

        System.out.println("Gerçek değer: " + n);
        System.out.println("HLL Tahmini: " + (long) hll.estimate());
    }
}


