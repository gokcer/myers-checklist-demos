// CP08 · Comparison · Does the way the Boolean expression is evaluated affect the program?
// Myers: if (x == 0 && (x/y) > z) is safe only if evaluation stops early. In Java, & does NOT stop early.
public class CP08ShortCircuit {

    static boolean fastBuggy(int distance, int minutes) {
        return minutes != 0 & distance / minutes > 2;      // BUG: & evaluates both sides -> / by zero
    }

    static boolean fastFixed(int distance, int minutes) {
        return minutes != 0 && distance / minutes > 2;     // FIX: && stops when the left side is false
    }

    public static void main(String[] args) {
        System.out.println("CP08 Short-circuit evaluation (Java)   distance 10, minutes 0");
        try {
            System.out.println("  BUG -> " + fastBuggy(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("  BUG -> ArithmeticException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + fastFixed(10, 0));
    }
}
