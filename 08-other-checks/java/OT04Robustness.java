// OT04 · Other · Is the module robust: does it check its input for validity?
// A negative quantity makes the shop PAY the customer (Juice Shop has a challenge like this).
public class OT04Robustness {

    static int totalBuggy(int unitPrice, int quantity) {
        // BUG: quantity is not checked; -3 is accepted
        return unitPrice * quantity;
    }

    static int totalFixed(int unitPrice, int quantity) {
        if (quantity < 1 || quantity > 99) {                   // FIX: validate at the border
            throw new IllegalArgumentException("quantity must be 1..99, got " + quantity);
        }
        return unitPrice * quantity;
    }

    public static void main(String[] args) {
        System.out.println("OT04 Robustness: input not checked (Java)   price 50, quantity -3");
        System.out.println("  BUG -> " + totalBuggy(50, -3));
        try {
            System.out.println("  FIX -> " + totalFixed(50, -3));
        } catch (IllegalArgumentException e) {
            System.out.println("  FIX -> IllegalArgumentException: " + e.getMessage());
        }
    }
}
