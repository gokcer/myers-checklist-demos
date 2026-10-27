// CP05 · Comparison · Are comparison and Boolean operators mixed up? (2<i<10, a==b==c, i>x||y)
// Java rejects 2 < i < 10 for ints, but a == b == c compiles when the values are booleans.
public class CP05BooleanOperands {

    // spec: true if all three sensors agree
    static boolean allAgreeBuggy(boolean a, boolean b, boolean c) {
        return a == b == c;                   // BUG: means (a == b) == c
    }

    static boolean allAgreeFixed(boolean a, boolean b, boolean c) {
        return a == b && b == c;              // FIX
    }

    public static void main(String[] args) {
        System.out.println("CP05 Boolean operands (Java)   sensors: false, false, true");
        System.out.println("  BUG -> " + allAgreeBuggy(false, false, true));
        System.out.println("  FIX -> " + allAgreeFixed(false, false, true));
    }
}
