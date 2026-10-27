// CF03 · Control flow · What happens if a loop runs zero times, or a search loop never finds anything?
public class CF03LoopFallThrough {

    static final String[] USERS = {"ada", "linus", "grace"};

    static String findBuggy(String name) {
        int i;
        for (i = 0; i < USERS.length; i++) {
            if (USERS[i].equals(name)) {
                break;
            }
        }
        return USERS[i];                                    // BUG: not found -> i == length
    }

    static String findFixed(String name) {
        int i;
        for (i = 0; i < USERS.length; i++) {
            if (USERS[i].equals(name)) {
                break;
            }
        }
        return i < USERS.length ? USERS[i] : "not found";   // FIX: handle the not-found case explicitly
    }

    public static void main(String[] args) {
        System.out.println("CF03 Search loop falls through (Java)   search 'alan'");
        try {
            System.out.println("  BUG -> " + findBuggy("alan"));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("  BUG -> ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        System.out.println("  FIX -> " + findFixed("alan"));
    }
}
