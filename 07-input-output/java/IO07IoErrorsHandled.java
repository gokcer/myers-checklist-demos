// IO07 · Input/output · Are I/O error conditions handled correctly (not hidden)?
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO07IoErrorsHandled {

    static final String TARGET = "this-folder-does-not-exist/backup.txt";

    static String backupBuggy() {
        String status = "backup done";
        try (FileWriter out = new FileWriter(TARGET, StandardCharsets.UTF_8)) {
            out.write("data");
        } catch (IOException e) {
            // BUG: error swallowed; the user is told it worked
        }
        return status;
    }

    static String backupFixed() {
        String status = "backup done";
        try (FileWriter out = new FileWriter(TARGET, StandardCharsets.UTF_8)) {
            out.write("data");
        } catch (IOException e) {
            status = "backup FAILED: " + e.getMessage();      // FIX: report it (or let it propagate)
        }
        return status;
    }

    public static void main(String[] args) {
        System.out.println("IO07 I/O error hidden (Java)   the folder does not exist");
        System.out.println("  BUG -> " + backupBuggy());
        System.out.println("  FIX -> " + backupFixed());
    }
}
