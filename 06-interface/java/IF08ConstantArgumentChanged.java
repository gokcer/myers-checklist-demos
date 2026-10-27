// IF08 · Interface · Is a "constant" passed as an argument and then changed by the callee?
// Myers' Fortran example: CALL SUBX(J,3) could change the value of 3. In Java: a static final ARRAY is not constant.
import java.util.Arrays;

public class IF08ConstantArgumentChanged {

    // final means: the NAME always points to the same array. The numbers inside can still change.
    static final int[] DEFAULT_LIMITS = {100, 500, 1000};      // used by the BUG demo
    static final int[] STANDARD_LIMITS = {100, 500, 1000};     // the same values, used by the FIX demo

    static int[] vipLimitsBuggy(int[] limits) {
        int[] result = limits;                     // BUG: the same array as the caller's "constant"
        for (int i = 0; i < result.length; i++) {
            result[i] *= 10;
        }
        return result;
    }

    static int[] vipLimitsFixed(int[] limits) {
        int[] result = limits.clone();             // FIX: change a copy, never the constant
        for (int i = 0; i < result.length; i++) {
            result[i] *= 10;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("IF08 Constant argument changed by the callee (Java)   defaults 100, 500, 1000");
        int[] vip = vipLimitsBuggy(DEFAULT_LIMITS);
        System.out.println("  BUG -> VIP " + Arrays.toString(vip) + ", defaults now " + Arrays.toString(DEFAULT_LIMITS));
        vip = vipLimitsFixed(STANDARD_LIMITS);
        System.out.println("  FIX -> VIP " + Arrays.toString(vip) + ", defaults now " + Arrays.toString(STANDARD_LIMITS));
    }
}
