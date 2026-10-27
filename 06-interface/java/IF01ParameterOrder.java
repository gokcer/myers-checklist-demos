// IF01 · Interface · Do the number and ORDER of arguments match the parameters (in both directions)?
// Two parameters of the same type can be swapped and the compiler cannot notice.
import java.util.HashMap;
import java.util.Map;

public class IF01ParameterOrder {

    static Map<String, Integer> accounts() {
        Map<String, Integer> acc = new HashMap<>();
        acc.put("ada", 100);
        acc.put("bob", 0);
        return acc;
    }

    // moves 'amount' FROM the first account TO the second one
    static void transfer(Map<String, Integer> acc, String from, String to, int amount) {
        acc.put(from, acc.get(from) - amount);
        acc.put(to, acc.get(to) + amount);
    }

    static String adaPaysBobBuggy() {
        Map<String, Integer> acc = accounts();
        transfer(acc, "bob", "ada", 30);        // BUG: arguments swapped; the money goes the wrong way
        return "ada=" + acc.get("ada") + ", bob=" + acc.get("bob");
    }

    static String adaPaysBobFixed() {
        Map<String, Integer> acc = accounts();
        transfer(acc, "ada", "bob", 30);        // FIX: the order of the parameters: from, to
        return "ada=" + acc.get("ada") + ", bob=" + acc.get("bob");
    }

    public static void main(String[] args) {
        System.out.println("IF01 Argument order (Java)   Ada pays Bob 30");
        System.out.println("  BUG -> " + adaPaysBobBuggy());
        System.out.println("  FIX -> " + adaPaysBobFixed());
    }
}
