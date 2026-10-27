// IO06 · Input/output · Is the end-of-file condition detected and handled correctly?
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class IO06EndOfFile {

    static final String FILE = "10\n20\n30\n";       // 3 lines

    static int sumBuggy() throws IOException {
        int sum = 0;
        try (BufferedReader in = new BufferedReader(new StringReader(FILE))) {
            for (int i = 0; i < 4; i++) {                 // BUG: assumes 4 lines
                String line = in.readLine();              // at end-of-file readLine() returns null
                sum += Integer.parseInt(line.trim());
            }
        }
        return sum;
    }

    static int sumFixed() throws IOException {
        int sum = 0;
        try (BufferedReader in = new BufferedReader(new StringReader(FILE))) {
            String line = in.readLine();
            while (line != null) {                        // FIX: stop at end-of-file
                sum += Integer.parseInt(line.trim());
                line = in.readLine();
            }
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO06 End of file (Java)   the file has 3 lines");
        try {
            System.out.println("  BUG -> " + sumBuggy());
        } catch (NullPointerException e) {
            System.out.println("  BUG -> NullPointerException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + sumFixed());
    }
}
