// CO01 · Computation · Is a computation done on values of a non-arithmetic type?
// In Java, + with a String on the left becomes text concatenation; a char is secretly a number.
public class CO01NonArithmeticTypes {

    static String totalLabelBuggy(int coffee, int cake) {
        return "Total: " + coffee + cake;           // BUG: "Total: 40" + 60 -> "Total: 4060"
    }

    static String totalLabelFixed(int coffee, int cake) {
        return "Total: " + (coffee + cake);         // FIX: add the numbers first
    }

    static int nextGradeBuggy(char grade) {
        return grade + 1;                           // BUG: 'B' + 1 is the number 67
    }

    static char nextGradeFixed(char grade) {
        return (char) (grade + 1);                  // FIX: turn the number back into a char
    }

    public static void main(String[] args) {
        System.out.println("CO01 Computation with non-arithmetic types (Java)   coffee 40, cake 60, grade B");
        System.out.println("  BUG label -> " + totalLabelBuggy(40, 60));
        System.out.println("  FIX label -> " + totalLabelFixed(40, 60));
        System.out.println("  BUG next grade -> " + nextGradeBuggy('B'));
        System.out.println("  FIX next grade -> " + nextGradeFixed('B'));
    }
}
