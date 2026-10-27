// CO02 · Computation · Mixed-mode computations: integers and floating point together.
// Myers' own example: int x = 1, y = 2; z = x / y; gives 0.
public class CO02MixedMode {

    static double averageBuggy(int sum, int count) {
        double avg = sum / count;            // BUG: integer division happens BEFORE the widening to double
        return avg;
    }

    static double averageFixed(int sum, int count) {
        double avg = (double) sum / count;   // FIX: make one operand double first
        return avg;
    }

    public static void main(String[] args) {
        System.out.println("CO02 Mixed-mode computation (Java)   average of 7 over 2");
        System.out.println("  BUG -> " + averageBuggy(7, 2));
        System.out.println("  FIX -> " + averageFixed(7, 2));
    }
}
