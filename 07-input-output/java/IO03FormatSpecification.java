// IO03 · Input/output · Does the format specification agree with the items being printed?
// javac does not check format strings; the error appears only when this line runs.
import java.util.IllegalFormatConversionException;
import java.util.MissingFormatArgumentException;

public class IO03FormatSpecification {

    static String pointsBuggy(String name, int score) {
        return String.format("%s scored %d points", score, name);       // BUG: arguments in the wrong order
    }

    static String pointsFixed(String name, int score) {
        return String.format("%s scored %d points", name, score);       // FIX: the order of the format
    }

    static String outOfBuggy(String name, int score) {
        return String.format("%s scored %d of %d", name, score);        // BUG: three %, two arguments
    }

    static String outOfFixed(String name, int score) {
        return String.format("%s scored %d of %d", name, score, 100);   // FIX: one argument for each %
    }

    public static void main(String[] args) {
        System.out.println("IO03 Format string does not match the arguments (Java)   Ada, 95");
        try {
            System.out.println("  BUG -> " + pointsBuggy("Ada", 95));
        } catch (IllegalFormatConversionException e) {
            System.out.println("  BUG -> IllegalFormatConversionException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + pointsFixed("Ada", 95));
        try {
            System.out.println("  BUG -> " + outOfBuggy("Ada", 95));
        } catch (MissingFormatArgumentException e) {
            System.out.println("  BUG -> MissingFormatArgumentException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + outOfFixed("Ada", 95));
    }
}
