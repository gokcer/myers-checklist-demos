// IO08 · Input/output · Are there spelling or grammar mistakes in the text the program shows?
public class IO08SpellingInOutput {

    static String messageBuggy(int n) {
        String word = "files";
        return "Sucessfully deleted " + n + " " + word;          // BUG: typo; and "1 files"
    }

    static String messageFixed(int n) {
        String word = n == 1 ? "file" : "files";
        return "Successfully deleted " + n + " " + word;         // FIX
    }

    public static void main(String[] args) {
        System.out.println("IO08 Spelling and grammar in output (Java)   1 file");
        System.out.println("  BUG -> " + messageBuggy(1));
        System.out.println("  FIX -> " + messageFixed(1));
    }
}
