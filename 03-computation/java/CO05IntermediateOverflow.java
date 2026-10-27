// CO05 · Computation · Can an intermediate result overflow, even if the final result fits?
// The famous binary-search bug (Joshua Bloch, 2006): (low + high) / 2 overflows for big arrays.
public class CO05IntermediateOverflow {

    static int midBuggy(int low, int high) {
        int mid = (low + high) / 2;          // BUG: low + high > Integer.MAX_VALUE
        return mid;
    }

    static int midFixed(int low, int high) {
        int mid = low + (high - low) / 2;    // FIX: the intermediate value always fits
        return mid;
    }

    public static void main(String[] args) {
        System.out.println("CO05 Intermediate overflow (Java)   low = 1500000000, high = 2000000000");
        System.out.println("  BUG -> " + midBuggy(1500000000, 2000000000));
        System.out.println("  FIX -> " + midFixed(1500000000, 2000000000));
    }
}
