// DD01 · Data declaration · Are all variables declared explicitly (with the type you mean)?
// `var` (Java 10) lets the compiler choose the type. Here it chooses int, and *= 1.5 silently truncates.
public class DD01ImplicitDeclaration {

    static double priceBuggy() {
        var price = 10;          // BUG: the compiler chooses int
        price *= 1.5;            // compound assignment casts the result back to int: 15
        price *= 1.25;           // 18.75 becomes 18
        return price;
    }

    static double priceFixed() {
        double price = 10;       // FIX: say the type you mean (or use BigDecimal for money)
        price *= 1.5;
        price *= 1.25;           // 18.75 stays 18.75
        return price;
    }

    public static void main(String[] args) {
        System.out.println("DD01 Implicit declaration with var (Java)   expected 18.75");
        System.out.println("  BUG -> " + priceBuggy());
        System.out.println("  FIX -> " + priceFixed());
    }
}
