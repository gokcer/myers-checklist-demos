// IO01 · Input/output · Are the declared attributes of the file correct? (here: the character encoding)
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class IO01FileEncoding {

    static File sampleFile() throws IOException {
        File file = File.createTempFile("io01", ".txt");
        try (FileWriter out = new FileWriter(file, StandardCharsets.UTF_8)) {
            out.write("Gökçer, İzmir, ğüşıö");                // the file is UTF-8
        }
        return file;
    }

    static String readBuggy(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.ISO_8859_1))) {
            return in.readLine();                             // BUG: the wrong encoding is declared
        }
    }

    static String readFixed(File file) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            return in.readLine();                             // FIX: the encoding the file really has
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("IO01 File encoding (Java)   the file is UTF-8");
        File file = sampleFile();
        System.out.println("  BUG -> " + readBuggy(file));
        System.out.println("  FIX -> " + readFixed(file));
    }
}
