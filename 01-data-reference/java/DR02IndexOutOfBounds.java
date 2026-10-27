// DR02 · Data reference · Is every index inside the bounds of the array?
public class DR02IndexOutOfBounds {

    static final int[] SCORES = {70, 85, 90};

    static int lastScoreBuggy() {
        return SCORES[SCORES.length];         // BUG: valid indexes are 0 .. length-1
    }

    static int lastScoreFixed() {
        return SCORES[SCORES.length - 1];     // FIX: the last index is length-1
    }

    public static void main(String[] args) {
        System.out.println("DR02 Index out of bounds (Java)");
        try {
            System.out.println("  BUG -> " + lastScoreBuggy());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  BUG -> ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + lastScoreFixed());
    }
}
