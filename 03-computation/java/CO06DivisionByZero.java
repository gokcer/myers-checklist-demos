// CO06 · Computation · Can the divisor be zero?
// With int, Java throws. With double, Java does NOT throw: you silently get Infinity or NaN.
public class CO06DivisionByZero {

    static double averageBuggy(double[] values) {
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.length;                             // BUG: no values -> 0.0 / 0 = NaN, no error
    }

    static double averageFixed(double[] values) {
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return values.length == 0 ? 0.0 : sum / values.length; // FIX: the spec decides what "no data" means
    }

    static int averageOfIntsBuggy(int[] values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum / values.length;                             // BUG: with int, Java throws
    }

    static int averageOfIntsFixed(int[] values) {
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return values.length == 0 ? 0 : sum / values.length;   // FIX
    }

    public static void main(String[] args) {
        System.out.println("CO06 Division by zero (Java)   no values");
        System.out.println("  BUG double -> " + averageBuggy(new double[0]));
        System.out.println("  FIX double -> " + averageFixed(new double[0]));
        try {
            System.out.println("  BUG int -> " + averageOfIntsBuggy(new int[0]));
        } catch (ArithmeticException e) {
            System.out.println("  BUG int -> ArithmeticException: " + e.getMessage());
        }
        System.out.println("  FIX int -> " + averageOfIntsFixed(new int[0]));
    }
}
