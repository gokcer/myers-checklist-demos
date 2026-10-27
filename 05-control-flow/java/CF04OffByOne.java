// CF04 · Control flow · Are there off-by-one errors (one iteration too many or too few)?
// Myers' example: for (int i = 0; i <= 10; i++) runs 11 times, not 10.
public class CF04OffByOne {

    static int countBuggy() {
        int n = 0;
        for (int i = 0; i <= 10; i++) {     // BUG (Myers): 11 iterations
            n++;
        }
        return n;
    }

    static int countFixed() {
        int n = 0;
        for (int i = 0; i < 10; i++) {      // FIX: 10 iterations
            n++;
        }
        return n;
    }

    // pages needed to show 'items' results, 'size' per page
    static int pagesBuggy(int items, int size) {
        return items / size;                // BUG: drops the last, partial page
    }

    static int pagesFixed(int items, int size) {
        return (items + size - 1) / size;   // FIX: round up
    }

    public static void main(String[] args) {
        System.out.println("CF04 Off by one (Java)");
        System.out.println("  BUG loop meant to run 10 times -> " + countBuggy());
        System.out.println("  FIX loop meant to run 10 times -> " + countFixed());
        System.out.println("  BUG pages for 21 items, 10 per page -> " + pagesBuggy(21, 10));
        System.out.println("  FIX pages for 21 items, 10 per page -> " + pagesFixed(21, 10));
    }
}
