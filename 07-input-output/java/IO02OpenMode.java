// IO02 · Input/output · Are the attributes of the OPEN correct? (append or overwrite?)
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO02OpenMode {

    static String readAll(File file) throws IOException {
        StringBuilder text = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            String line = in.readLine();
            while (line != null) {
                text.append(text.length() == 0 ? "" : " | ").append(line);
                line = in.readLine();
            }
        }
        return text.toString();
    }

    static String logBuggy(File log) throws IOException {
        String[] events = {"login", "logout"};
        for (String event : events) {
            try (FileWriter out = new FileWriter(log, StandardCharsets.UTF_8)) {          // BUG: opening without "append" empties the file
                out.write(event + "\n");
            }
        }
        return readAll(log);
    }

    static String logFixed(File log) throws IOException {
        String[] events = {"login", "logout"};
        for (String event : events) {
            try (FileWriter out = new FileWriter(log, StandardCharsets.UTF_8, true)) {    // FIX: append = true
                out.write(event + "\n");
            }
        }
        return readAll(log);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO02 Open mode: overwrite instead of append (Java)   events: login, logout");
        System.out.println("  BUG -> " + logBuggy(File.createTempFile("io02", ".log")));
        System.out.println("  FIX -> " + logFixed(File.createTempFile("io02", ".log")));
    }
}
