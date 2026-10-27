// CP01 · Comparison · Are values of different types compared (text with a number, Long with Integer)?
// equals() between different wrapper types is always false, and it compiles.
import java.util.HashMap;
import java.util.Map;

public class CP01DifferentTypes {

    static final Map<Long, String> USERS = new HashMap<>();

    static {
        USERS.put(42L, "Ada");
    }

    static boolean sameIdBuggy(Long userId, int requestId) {
        return userId.equals(requestId);                // BUG: Long.equals(Integer) is always false
    }

    static boolean sameIdFixed(Long userId, int requestId) {
        return userId.equals((long) requestId);         // FIX: same type on both sides
    }

    static String findBuggy(int id) {
        return USERS.get(id);                           // BUG: key is Integer 42, the map holds Long 42 -> null
    }

    static String findFixed(int id) {
        return USERS.get((long) id);                    // FIX: same type on both sides
    }

    public static void main(String[] args) {
        System.out.println("CP01 Comparison of different types (Java)   user id 42");
        System.out.println("  BUG Long 42 equals int 42? -> " + sameIdBuggy(42L, 42));
        System.out.println("  FIX Long 42 equals int 42? -> " + sameIdFixed(42L, 42));
        System.out.println("  BUG map lookup -> " + findBuggy(42));
        System.out.println("  FIX map lookup -> " + findFixed(42));
    }
}
