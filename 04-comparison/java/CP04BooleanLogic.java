// CP04 · Comparison · Does each Boolean expression say what it should? (and, or, not)
public class CP04BooleanLogic {

    // spec: the office is open on weekdays (NOT Saturday and NOT Sunday)
    static boolean isWeekdayBuggy(String day) {
        return !day.equals("Sat") || !day.equals("Sun");   // BUG: always true
    }

    static boolean isWeekdayFixed(String day) {
        return !day.equals("Sat") && !day.equals("Sun");   // FIX: De Morgan: not(a or b) = not a and not b
    }

    public static void main(String[] args) {
        System.out.println("CP04 Boolean logic (Java)   Sunday");
        System.out.println("  BUG -> " + isWeekdayBuggy("Sun"));
        System.out.println("  FIX -> " + isWeekdayFixed("Sun"));
    }
}
