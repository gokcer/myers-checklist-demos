// CP02 · Comparison · Mixed-mode comparisons, or comparisons of different lengths (float vs double).
public class CP02MixedModeComparison {

    static boolean isTenPercentBuggy(float rate) {
        return rate == 0.1;                   // BUG: float 0.1f widened to double is not double 0.1
    }

    static boolean isTenPercentFixed(float rate) {
        return rate == 0.1f;                  // FIX: same type and length (better: a tolerance, see CP06)
    }

    public static void main(String[] args) {
        System.out.println("CP02 Mixed-mode comparison (Java)   rate = 0.1f");
        System.out.println("  BUG -> " + isTenPercentBuggy(0.1f));
        System.out.println("  FIX -> " + isTenPercentFixed(0.1f));
    }
}
