// IO05 · Input/output · Is every file closed after use?
// Data waits in a buffer until the writer is flushed or closed. Without close() it may never reach the disk.
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO05FileNotClosed {

    static String readFirstLine(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = in.readLine();
            return line == null ? "" : line;
        }
    }

    static String saveBuggy(File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8));
        out.write("order 1001: paid");
        // BUG: out.close() is missing, so the text is still in the buffer
        return "file now contains: '" + readFirstLine(file) + "'";
    }

    static String saveFixed(File file) throws IOException {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            out.write("order 1001: paid");    // FIX: try-with-resources closes the file, and the buffer reaches the disk
        }
        return "file now contains: '" + readFirstLine(file) + "'";
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO05 File not closed (Java)");
        System.out.println("  BUG -> " + saveBuggy(File.createTempFile("io05", ".txt")));
        System.out.println("  FIX -> " + saveFixed(File.createTempFile("io05", ".txt")));
    }
}
