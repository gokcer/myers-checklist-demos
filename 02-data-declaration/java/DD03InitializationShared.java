// DD03 · Data declaration · Is a variable initialized correctly where it is declared?
// A list created once and added three times is ONE list with three names.
import java.util.ArrayList;
import java.util.List;

public class DD03InitializationShared {

    static String shelvesBuggy() {
        List<List<String>> shelves = new ArrayList<>();
        List<String> shelf = new ArrayList<>();         // BUG: created once, before the loop
        for (int i = 0; i < 3; i++) {
            shelves.add(shelf);
        }
        shelves.get(0).add("book");
        return shelves.toString();
    }

    static String shelvesFixed() {
        List<List<String>> shelves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> shelf = new ArrayList<>();     // FIX: a new list in every iteration
            shelves.add(shelf);
        }
        shelves.get(0).add("book");
        return shelves.toString();
    }

    public static void main(String[] args) {
        System.out.println("DD03 Initialization shares one object (Java)   put a book on shelf 0");
        System.out.println("  BUG -> " + shelvesBuggy());
        System.out.println("  FIX -> " + shelvesFixed());
    }
}
