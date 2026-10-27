// CO09 · Computation · Are the assumptions about operator precedence correct?
public class CO09OperatorPrecedence {

    static int averageBuggy(int a, int b) {
        return a + b / 2;              // BUG: / before +  ->  a + (b / 2)
    }

    static int averageFixed(int a, int b) {
        return (a + b) / 2;            // FIX: parentheses say what you mean
    }

    static int nextFlagsBuggy(int flags) {
        return flags << 1 + 1;         // BUG: + before <<  ->  flags << 2
    }

    static int nextFlagsFixed(int flags) {
        return (flags << 1) + 1;       // FIX
    }

    public static void main(String[] args) {
        System.out.println("CO09 Operator precedence (Java)   a = 80, b = 100, flags = 1");
        System.out.println("  BUG average -> " + averageBuggy(80, 100));
        System.out.println("  FIX average -> " + averageFixed(80, 100));
        System.out.println("  BUG flags -> " + nextFlagsBuggy(1));
        System.out.println("  FIX flags -> " + nextFlagsFixed(1));
    }
}
