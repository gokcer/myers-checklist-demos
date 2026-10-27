// CP03 · Comparison · Is the comparison operator correct? ("at least", "at most", "more than")
public class CP03WrongOperator {

    // spec: free shipping for orders of AT LEAST 500 TL
    static boolean freeShippingBuggy(int total) {
        return total > 500;                   // BUG: "more than", not "at least"
    }

    static boolean freeShippingFixed(int total) {
        return total >= 500;                  // FIX
    }

    public static void main(String[] args) {
        System.out.println("CP03 Wrong comparison operator (Java)   order of exactly 500 TL");
        System.out.println("  BUG -> " + freeShippingBuggy(500));
        System.out.println("  FIX -> " + freeShippingFixed(500));
    }
}
