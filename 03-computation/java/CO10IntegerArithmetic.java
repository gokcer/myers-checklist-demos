// CO10 · Computation · Are there invalid uses of integer arithmetic, especially division?
// Myers: 2*i/2 == i depends on whether i is odd or even and on the order of the operations.
public class CO10IntegerArithmetic {

    static int percentBuggy(int done, int total) {
        return done / total * 100;          // BUG: 45 / 60 is 0 in integer arithmetic
    }

    static int percentFixed(int done, int total) {
        return done * 100 / total;          // FIX: multiply first (or use double)
    }

    static int doubleThenHalve(int i) {
        return 2 * i / 2;                   // (2 * 7) / 2 = 7
    }

    static int halveThenDouble(int i) {
        return 2 * (i / 2);                 // 2 * (7 / 2) = 2 * 3 = 6
    }

    public static void main(String[] args) {
        System.out.println("CO10 Integer arithmetic (Java)");
        System.out.println("  BUG percent 45 of 60 -> " + percentBuggy(45, 60));
        System.out.println("  FIX percent 45 of 60 -> " + percentFixed(45, 60));
        System.out.println("  Myers 2 * i / 2   (i = 7) -> " + doubleThenHalve(7));
        System.out.println("  Myers 2 * (i / 2) (i = 7) -> " + halveThenDouble(7));
    }
}
