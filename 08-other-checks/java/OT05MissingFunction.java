// OT05 · Other · Is a function missing?
// Spec: "A withdrawal must not make the balance negative." The check simply does not exist.
// No tool can find this: only a reviewer who compares the code with the specification.
public class OT05MissingFunction {

    static final class AccountBuggy {
        int balance;

        AccountBuggy(int balance) {
            this.balance = balance;
        }

        int withdraw(int amount) {
            // BUG: the overdraft check is missing
            balance -= amount;
            return balance;
        }
    }

    static final class AccountFixed {
        int balance;

        AccountFixed(int balance) {
            this.balance = balance;
        }

        int withdraw(int amount) {
            if (amount > balance) {                                   // FIX: the check the spec asks for
                throw new IllegalStateException("insufficient funds");
            }
            balance -= amount;
            return balance;
        }
    }

    public static void main(String[] args) {
        System.out.println("OT05 Missing function (Java)   balance 100, withdraw 150");
        System.out.println("  BUG -> " + new AccountBuggy(100).withdraw(150));
        try {
            System.out.println("  FIX -> " + new AccountFixed(100).withdraw(150));
        } catch (IllegalStateException e) {
            System.out.println("  FIX -> IllegalStateException: " + e.getMessage());
        }
    }
}
