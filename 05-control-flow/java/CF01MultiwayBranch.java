// CF01 · Control flow · In a multiway branch (switch, computed GOTO), can the selector take a value
// that has no branch? Java adds a second trap: an old-style case without break falls through.
public class CF01MultiwayBranch {

    static int shippingBuggy(int zone) {
        int fee = 0;
        switch (zone) {
            case 1: fee = 20;           // BUG: no break -> falls through to case 2
            case 2: fee = 35; break;
            case 3: fee = 50; break;    // BUG: no default -> zone 4 silently costs 0
        }
        return fee;
    }

    static int shippingFixed(int zone) {
        int fee = 0;
        switch (zone) {
            case 1: fee = 20; break;    // FIX: a break in every case
            case 2: fee = 35; break;
            case 3: fee = 50; break;
            default: throw new IllegalArgumentException("unknown zone " + zone);   // FIX: no silent default
        }
        return fee;
    }

    public static void main(String[] args) {
        System.out.println("CF01 Multiway branch (Java)");
        System.out.println("  BUG zone 1 -> " + shippingBuggy(1));
        System.out.println("  FIX zone 1 -> " + shippingFixed(1));
        System.out.println("  BUG zone 4 -> " + shippingBuggy(4));
        try {
            System.out.println("  FIX zone 4 -> " + shippingFixed(4));
        } catch (IllegalArgumentException e) {
            System.out.println("  FIX zone 4 -> IllegalArgumentException: " + e.getMessage());
        }
    }
}
