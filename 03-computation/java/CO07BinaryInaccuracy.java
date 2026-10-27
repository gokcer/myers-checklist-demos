// CO07 · Computation · Base-2 inaccuracy: 10 x 0.1 is rarely exactly 1.0.
import java.math.BigDecimal;

public class CO07BinaryInaccuracy {

    static String sumBuggy() {
        double total = 0;
        for (int i = 0; i < 10; i++) {
            total = total + 0.1;                              // BUG: 0.1 has no exact binary form
        }
        return String.valueOf(total);
    }

    static String sumFixed() {
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < 10; i++) {
            total = total.add(new BigDecimal("0.1"));         // FIX: decimal arithmetic
        }
        return String.valueOf(total);
    }

    public static void main(String[] args) {
        System.out.println("CO07 Base-2 inaccuracy (Java)   10 x 0.1");
        System.out.println("  BUG -> " + sumBuggy());
        System.out.println("  FIX -> " + sumFixed());
    }
}
