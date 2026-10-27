// DD05 · Data declaration · Is the initialization consistent with how long the variable lives?
// Myers' example: a Fortran DATA statement runs once, not on every call. In Java: a static field.
public class DD05StaticVsPerCall {

    static final class LoginBuggy {
        static int failedAttempts = 0;        // BUG: static: initialized ONCE, shared by all users

        boolean tryLogin(boolean passwordOk) {
            if (!passwordOk) {
                failedAttempts++;
            }
            return failedAttempts < 3;        // "still allowed?"
        }
    }

    static final class LoginFixed {
        int failedAttempts = 0;               // FIX: one counter for each login object (each user)

        boolean tryLogin(boolean passwordOk) {
            if (!passwordOk) {
                failedAttempts++;
            }
            return failedAttempts < 3;
        }
    }

    static String loginsBuggy() {
        LoginBuggy ada = new LoginBuggy();
        ada.tryLogin(false);
        boolean adaAllowed = ada.tryLogin(false);
        LoginBuggy bob = new LoginBuggy();
        boolean bobAllowed = bob.tryLogin(false);
        return "Ada allowed: " + adaAllowed + ", Bob allowed: " + bobAllowed;
    }

    static String loginsFixed() {
        LoginFixed ada = new LoginFixed();
        ada.tryLogin(false);
        boolean adaAllowed = ada.tryLogin(false);
        LoginFixed bob = new LoginFixed();
        boolean bobAllowed = bob.tryLogin(false);
        return "Ada allowed: " + adaAllowed + ", Bob allowed: " + bobAllowed;
    }

    public static void main(String[] args) {
        System.out.println("DD05 Static vs per-user initialization (Java)   Ada fails twice, then Bob fails once");
        System.out.println("  BUG -> " + loginsBuggy());
        System.out.println("  FIX -> " + loginsFixed());
    }
}
