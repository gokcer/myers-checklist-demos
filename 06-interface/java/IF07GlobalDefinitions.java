// IF07 · Interface · Do global (shared) values have the same definition in every module?
public class IF07GlobalDefinitions {

    // BUG: every module has its own copy of the VAT rate, and one copy is old
    static final class CheckoutBuggy {
        static final double VAT = 0.20;
        static double gross(double net) { return net * (1 + VAT); }
    }

    static final class InvoiceBuggy {
        static final double VAT = 0.18;                                    // old rate
        static double gross(double net) { return net * (1 + VAT); }
    }

    // FIX: ONE definition, and every module reads it
    static final class Tax {
        static final double VAT = 0.20;
    }

    static final class CheckoutFixed {
        static double gross(double net) { return net * (1 + Tax.VAT); }
    }

    static final class InvoiceFixed {
        static double gross(double net) { return net * (1 + Tax.VAT); }
    }

    public static void main(String[] args) {
        System.out.println("IF07 Global defined differently in two modules (Java)   net 100");
        System.out.println("  BUG checkout vs invoice -> " + CheckoutBuggy.gross(100) + " vs " + InvoiceBuggy.gross(100));
        System.out.println("  FIX checkout vs invoice -> " + CheckoutFixed.gross(100) + " vs " + InvoiceFixed.gross(100));
    }
}
