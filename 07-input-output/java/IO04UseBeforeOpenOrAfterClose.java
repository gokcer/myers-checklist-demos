// IO04 · Input/output · Is every file open when it is used? (and: does the program try to load a whole file into memory?)
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO04UseBeforeOpenOrAfterClose {

    static String writeBuggy(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        out.write("header\n");
        out.close();
        out.write("row 1\n");                 // BUG: the writer is already closed
        return "written";
    }

    static String writeFixed(File file) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            out.write("header\n");
            out.write("row 1\n");             // FIX: every write inside the block; it closes the file at the end
        }
        return "written";
    }

    // big files: read one line at a time instead of loading the whole file into memory
    static int countLines(File file) throws IOException {
        int count = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = in.readLine();
            while (line != null) {
                count++;
                line = in.readLine();
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO04 File used after it was closed (Java)");
        File file = File.createTempFile("io04", ".csv");
        try {
            System.out.println("  BUG -> " + writeBuggy(file));
        } catch (IOException e) {
            System.out.println("  BUG -> IOException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + writeFixed(file));
        System.out.println("  lines in the file (read line by line) -> " + countLines(file));
    }
}
