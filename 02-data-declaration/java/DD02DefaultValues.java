// DD02 · Data declaration · If an attribute is not stated, is its default well understood?
// Myers: "the default attributes received in Java are often a source of surprise".
public class DD02DefaultValues {

    static final class UserBuggy {
        String name;
        boolean active;          // BUG: not set -> default false; every new user is "inactive"
        int loginLimit;          // BUG: default 0 -> nobody can log in

        UserBuggy(String name) {
            this.name = name;
        }
    }

    static final class UserFixed {
        String name;
        boolean active = true;   // FIX: state the value you mean
        int loginLimit = 5;

        UserFixed(String name) {
            this.name = name;
        }
    }

    static String describeBuggy() {
        UserBuggy u = new UserBuggy("Ada");
        return "active=" + u.active + ", loginLimit=" + u.loginLimit;
    }

    static String describeFixed() {
        UserFixed u = new UserFixed("Ada");
        return "active=" + u.active + ", loginLimit=" + u.loginLimit;
    }

    public static void main(String[] args) {
        System.out.println("DD02 Default values (Java)   a new user");
        System.out.println("  BUG -> " + describeBuggy());
        System.out.println("  FIX -> " + describeFixed());
    }
}
