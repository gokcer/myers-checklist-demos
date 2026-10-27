// DR10 · Data reference · Are string index limits off by one?
public class DR10StringOffByOne {

    // spec: return the file extension WITHOUT the dot: "report.pdf" -> "pdf"
    static String extensionBuggy(String file) {
        int start = file.lastIndexOf('.');          // BUG: starts at the dot -> ".pdf"
        return file.substring(start);
    }

    static String extensionFixed(String file) {
        int start = file.lastIndexOf('.') + 1;      // FIX: one character after the dot
        return file.substring(start);
    }

    // spec: the first and the LAST letter: "Turing" -> "Tg"
    static String endsBuggy(String w) {
        int last = w.length();                      // BUG: the last index is length()-1
        return "" + w.charAt(0) + w.charAt(last);
    }

    static String endsFixed(String w) {
        int last = w.length() - 1;                  // FIX
        return "" + w.charAt(0) + w.charAt(last);
    }

    public static void main(String[] args) {
        System.out.println("DR10 String index off by one (Java)");
        System.out.println("  BUG extension -> " + extensionBuggy("report.pdf"));
        System.out.println("  FIX extension -> " + extensionFixed("report.pdf"));
        try {
            System.out.println("  BUG ends -> " + endsBuggy("Turing"));
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("  BUG ends -> StringIndexOutOfBoundsException: " + e.getMessage());
        }
        System.out.println("  FIX ends -> " + endsFixed("Turing"));
    }
}
