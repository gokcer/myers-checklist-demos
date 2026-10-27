// CP06 · Comparison · Are floating-point numbers compared for exact equality?
public class CP06FloatEquality {

    static final int SAFETY = 1000;           // only so that this demo stops; real code has no such guard

    static boolean paidInFullBuggy(double paid, double price) {
        return paid == price;                               // BUG: 0.1 + 0.2 is not exactly 0.3
    }

    static boolean paidInFullFixed(double paid, double price) {
        return Math.abs(paid - price) < 1e-9;               // FIX: compare with a tolerance
    }

    // count the 0.1 steps from 0 to 1 (expected 10)
    static int stepsBuggy() {
        int steps = 0;
        for (double x = 0.0; x != 1.0 && steps < SAFETY; x += 0.1) {   // BUG: x never equals 1.0 exactly
            steps++;
        }
        return steps;
    }

    static int stepsFixed() {
        int steps = 0;
        for (double x = 0.0; x < 1.0 - 1e-9 && steps < SAFETY; x += 0.1) {   // FIX: compare with a tolerance
            steps++;
        }
        return steps;
    }

    public static void main(String[] args) {
        System.out.println("CP06 Floating-point equality (Java)");
        System.out.println("  BUG paid 0.1 + 0.2 for a price of 0.3 -> " + paidInFullBuggy(0.1 + 0.2, 0.3));
        System.out.println("  FIX paid 0.1 + 0.2 for a price of 0.3 -> " + paidInFullFixed(0.1 + 0.2, 0.3));
        System.out.println("  BUG steps from 0 to 1 (1000 = stopped by the guard) -> " + stepsBuggy());
        System.out.println("  FIX steps from 0 to 1 -> " + stepsFixed());
    }
}
