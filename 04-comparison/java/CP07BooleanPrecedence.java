// CP07 · Comparison · Are the assumptions about && / || precedence correct?
public class CP07BooleanPrecedence {

    // spec: discount for MEMBERS who are children (< 12) or seniors (> 65)
    static boolean discountBuggy(boolean member, int age) {
        return member && age < 12 || age > 65;        // BUG: && binds first -> every senior gets it
    }

    static boolean discountFixed(boolean member, int age) {
        return member && (age < 12 || age > 65);      // FIX: parentheses say what you mean
    }

    public static void main(String[] args) {
        System.out.println("CP07 Boolean precedence (Java)   non-member, age 70");
        System.out.println("  BUG -> " + discountBuggy(false, 70));
        System.out.println("  FIX -> " + discountFixed(false, 70));
    }
}
