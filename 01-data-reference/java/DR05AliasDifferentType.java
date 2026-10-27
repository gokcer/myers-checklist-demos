// DR05 · Data reference · Is the same data reached through two names (aliases) with different attributes?
// Myers' example is Fortran EQUIVALENCE. In Java a raw-type alias of a List<String> lets a wrong value in.
import java.util.ArrayList;
import java.util.List;

public class DR05AliasDifferentType {

    static String secondNameBuggy() {
        List<String> names = new ArrayList<>();
        names.add("Ada");
        List legacy = names;              // BUG: second name for the same list, without the <String> attribute
        legacy.add(42);                   // compiles; javac only gives an "unchecked" warning
        String second = names.get(1);     // ClassCastException, far away from the real mistake
        return second;
    }

    static String secondNameFixed() {
        List<String> names = new ArrayList<>();
        names.add("Ada");
        List<String> legacy = names;      // FIX: every alias keeps the full type
        legacy.add("42");                 // legacy.add(42) would not compile now
        String second = names.get(1);
        return second;
    }

    public static void main(String[] args) {
        System.out.println("DR05 Alias with a different attribute (Java)");
        try {
            System.out.println("  BUG -> " + secondNameBuggy());
        } catch (ClassCastException e) {
            System.out.println("  BUG -> ClassCastException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + secondNameFixed());
    }
}
