// DR01 · Data reference · Is a variable (or array element, or field) used before it gets a value?
public class DR01UnsetVariable {

    static int totalNameLengthBuggy() {
        String[] names = new String[3];   // BUG: three slots, each starts as null
        names[0] = "Ada";
        names[1] = "Linus";               // names[2] never gets a value
        int total = 0;
        for (String n : names) {
            total += n.length();
        }
        return total;
    }

    static int totalNameLengthFixed() {
        String[] names = new String[2];   // FIX: exactly as many slots as values that are set
        names[0] = "Ada";
        names[1] = "Linus";
        int total = 0;
        for (String n : names) {
            total += n.length();
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println("DR01 Unset variable (Java)");
        try {
            System.out.println("  BUG -> " + totalNameLengthBuggy());
        } catch (NullPointerException e) {
            System.out.println("  BUG -> NullPointerException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + totalNameLengthFixed());
    }
}
