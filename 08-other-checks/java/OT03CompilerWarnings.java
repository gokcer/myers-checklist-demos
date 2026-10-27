// OT03 · Other · Did the compiler print warnings? Read every one.
// Compile this file with   javac -Xlint:all OT03CompilerWarnings.java   and look at the warnings.
import java.util.ArrayList;
import java.util.List;

public class OT03CompilerWarnings {

    static String levelBuggy(int score) {
        String level = "";
        switch (score / 10) {
            case 10:
            case 9: level = "A";            // [fallthrough] warning: no break
            case 8: level = "B"; break;
            default: level = "C";
        }
        return level;
    }

    static String levelFixed(int score) {
        String level = "";
        switch (score / 10) {
            case 10:
            case 9: level = "A"; break;     // FIX: the break the warning asked for
            case 8: level = "B"; break;
            default: level = "C";
        }
        return level;
    }

    // spec: share 10 apples between 2 people
    static int shareBuggy() {
        int each = 10 / 0;                  // [divzero] warning: the author typed 0 instead of 2
        return each;
    }

    static int shareFixed() {
        int each = 10 / 2;                  // FIX
        return each;
    }

    static List<String> namesBuggy() {
        List names = new ArrayList();       // [rawtypes] warning: a list without an element type
        names.add("Ada");                   // [unchecked] warning
        return names;                       // [unchecked] warning
    }

    static List<String> namesFixed() {
        List<String> names = new ArrayList<>();   // FIX: say the element type
        names.add("Ada");
        return names;
    }

    public static void main(String[] args) {
        System.out.println("OT03 Compiler warnings that were ignored (Java)   run javac -Xlint:all to see them");
        System.out.println("  BUG level for score 95 (expected A) -> " + levelBuggy(95));
        System.out.println("  FIX level for score 95 -> " + levelFixed(95));
        try {
            System.out.println("  BUG apples each -> " + shareBuggy());
        } catch (ArithmeticException e) {
            System.out.println("  BUG apples each -> ArithmeticException: " + e.getMessage());
        }
        System.out.println("  FIX apples each -> " + shareFixed());
        System.out.println("  BUG names -> " + namesBuggy());
        System.out.println("  FIX names -> " + namesFixed());
    }
}
