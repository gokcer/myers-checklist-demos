// IF05 · Interface · With several entry points, is a parameter used that this entry point never received?
// Myers' example is a PL/I ENTRY statement. In Java: two constructors, one forgets a field.
public class IF05MultipleEntryPoints {

    static final class PriceBuggy {
        final long amount;
        String currency;

        PriceBuggy(long amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        PriceBuggy(long amount) {
            this.amount = amount;                  // BUG: this entry point never sets currency
        }

        String label() {
            return amount + " " + currency.toUpperCase();
        }
    }

    static final class PriceFixed {
        final long amount;
        final String currency;                     // FIX: final makes the compiler check every constructor

        PriceFixed(long amount, String currency) {
            this.amount = amount;
            this.currency = currency;
        }

        PriceFixed(long amount) {
            this(amount, "try");                   // FIX: and this one calls the full constructor
        }

        String label() {
            return amount + " " + currency.toUpperCase();
        }
    }

    public static void main(String[] args) {
        System.out.println("IF05 Multiple entry points (Java)   new Price(100)");
        try {
            System.out.println("  BUG -> " + new PriceBuggy(100).label());
        } catch (NullPointerException e) {
            System.out.println("  BUG -> NullPointerException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + new PriceFixed(100).label());
    }
}
