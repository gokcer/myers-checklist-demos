// DD04 · Data declaration · Does each variable have the right type and length?
import java.math.BigDecimal;

public class DD04TypeAndLength {

    // milliseconds in 30 days: 2,592,000,000 does not fit into int (max 2,147,483,647)
    static long millisBuggy() {
        int ms = 30 * 24 * 60 * 60 * 1000;    // BUG: int is too short
        return ms;
    }

    static long millisFixed() {
        long ms = 30L * 24 * 60 * 60 * 1000;  // FIX: long, and the L makes the whole calculation long
        return ms;
    }

    static String moneyBuggy() {
        double total = 0.10 + 0.20;                                    // BUG: double for money
        return String.valueOf(total);
    }

    static String moneyFixed() {
        BigDecimal total = new BigDecimal("0.10").add(new BigDecimal("0.20"));   // FIX: a decimal type for money
        return String.valueOf(total);
    }

    public static void main(String[] args) {
        System.out.println("DD04 Wrong type or length (Java)");
        System.out.println("  BUG millis in 30 days -> " + millisBuggy());
        System.out.println("  FIX millis in 30 days -> " + millisFixed());
        System.out.println("  BUG 0.10 + 0.20 -> " + moneyBuggy());
        System.out.println("  FIX 0.10 + 0.20 -> " + moneyFixed());
    }
}
