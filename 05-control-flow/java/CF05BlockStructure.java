// CF05 · Control flow · Does each code block start and end where it should?
// An if without braces controls ONE statement. The second, indented statement always runs.
// This is the shape of Apple's "goto fail" bug (2014).
public class CF05BlockStructure {

    static String withdrawBuggy(int balance, int amount) {
        String result = "OK, new balance " + (balance - amount);
        if (amount > balance)
            System.out.println("    (log) insufficient funds");
            result = "REJECTED";              // BUG: looks inside the if, but always runs
        return result;
    }

    static String withdrawFixed(int balance, int amount) {
        String result = "OK, new balance " + (balance - amount);
        if (amount > balance) {               // FIX: always use braces
            System.out.println("    (log) insufficient funds");
            result = "REJECTED";
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("CF05 Block structure (Java)   balance 100, withdraw 30");
        System.out.println("  BUG -> " + withdrawBuggy(100, 30));
        System.out.println("  FIX -> " + withdrawFixed(100, 30));
    }
}
