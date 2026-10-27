// DR03 · Data reference · Does every index have an integer value?
// Java refuses a double index, so programmers cast. The cast truncates, and 0.29 * 100 is 28.999999999999996.
public class DR03NonIntegerIndex {

    // LABEL[k] is the label for a price of k kuruş (0..99)
    static final String[] LABEL = new String[100];

    static {
        for (int k = 0; k < 100; k++) {
            LABEL[k] = k + " kuruş";
        }
    }

    static String labelBuggy(double priceLira) {
        int index = (int) (priceLira * 100);                // BUG: (int) truncates 28.999999999999996 to 28
        return LABEL[index];
    }

    static String labelFixed(double priceLira) {
        int index = (int) Math.round(priceLira * 100);      // FIX: round first (or keep money as integer kuruş)
        return LABEL[index];
    }

    public static void main(String[] args) {
        System.out.println("DR03 Non-integer index (Java)   price = 0.29 lira");
        System.out.println("  BUG -> " + labelBuggy(0.29));
        System.out.println("  FIX -> " + labelFixed(0.29));
    }
}
