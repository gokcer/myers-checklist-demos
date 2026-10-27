// CF06 · Control flow · Are there non-exhaustive decisions? ("not 1 or 2, so it must be 3")
public class CF06NonExhaustiveDecision {

    // payment method: "card", "cash" or "voucher"
    static double feeBuggy(String method) {
        double fee;
        if (method.equals("card")) {
            fee = 2.0;
        } else if (method.equals("cash")) {
            fee = 0.0;
        } else {
            fee = 1.0;                            // BUG: assumes "voucher"; a typo "Card" also lands here
        }
        return fee;
    }

    static double feeFixed(String method) {
        double fee;
        if (method.equals("card")) {
            fee = 2.0;
        } else if (method.equals("cash")) {
            fee = 0.0;
        } else if (method.equals("voucher")) {    // FIX: name the last case too ...
            fee = 1.0;
        } else {                                  // ... and reject the values the spec does not allow
            throw new IllegalArgumentException("unknown payment method: " + method);
        }
        return fee;
    }

    public static void main(String[] args) {
        System.out.println("CF06 Non-exhaustive decision (Java)   method = \"Card\"");
        System.out.println("  BUG -> " + feeBuggy("Card"));
        try {
            System.out.println("  FIX -> " + feeFixed("Card"));
        } catch (IllegalArgumentException e) {
            System.out.println("  FIX -> IllegalArgumentException: " + e.getMessage());
        }
    }
}
