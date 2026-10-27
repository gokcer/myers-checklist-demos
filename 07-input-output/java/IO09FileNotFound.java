// IO09 · Input/output · Does the program handle "file not found" properly?
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO09FileNotFound {

    static final String CONFIG = "settings-that-are-not-there.properties";

    static String readFirstLine(String path) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            String line = in.readLine();
            return line == null ? "" : line.trim();
        }
    }

    static String themeBuggy() throws IOException {
        String theme;
        theme = readFirstLine(CONFIG);                       // BUG: missing file -> a crash for the user
        return theme;
    }

    static String themeFixed() throws IOException {
        String theme;
        try {
            theme = readFirstLine(CONFIG);
        } catch (FileNotFoundException e) {
            theme = "light (default, no settings file)";     // FIX: the spec's fallback
        }
        return theme;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO09 File not found (Java)");
        try {
            System.out.println("  BUG -> " + themeBuggy());
        } catch (FileNotFoundException e) {
            System.out.println("  BUG -> FileNotFoundException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + themeFixed());
    }
}
