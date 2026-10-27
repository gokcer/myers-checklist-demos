// OT01 · Other · Are there variables that are never used, or used only once?
// Myers: look at the compiler's cross-reference listing. Today a linter does this.
// A value that is computed but never used often means a missing step: here the discount is never applied.
public class OT01UnusedOrUsedOnce {

    static double totalBuggy(double price, boolean member) {
        double discount = member ? 0.10 : 0.0;     // BUG: assigned, never used ("dead store")
        double total = price;
        return total;
    }

    static double totalFixed(double price, boolean member) {
        double discount = member ? 0.10 : 0.0;
        double total = price * (1 - discount);     // FIX: the missing step
        return total;
    }

    public static void main(String[] args) {
        System.out.println("OT01 Variable never used (Java)   member, price 100");
        System.out.println("  BUG -> " + totalBuggy(100, true));
        System.out.println("  FIX -> " + totalFixed(100, true));
    }
}
