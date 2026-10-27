// CO04 · Computation · Is the target of an assignment smaller than the result?
// Compound assignment (+=) casts silently; an explicit cast to byte cuts the high bits.
public class CO04TargetTooSmall {

    static int totalBuggy() {
        short total = 30000;       // BUG: short holds at most 32767
        total += 10000;            // 40000 does not fit; += casts without a word
        return total;
    }

    static int totalFixed() {
        int total = 30000;         // FIX: a target big enough for the result
        total += 10000;
        return total;
    }

    static int brightnessBuggy(int level) {
        byte stored = (byte) level;                 // BUG: 200 does not fit into a byte (-128..127)
        return stored;
    }

    static int brightnessFixed(int level) {
        int stored = level;                         // FIX: a type that can hold 0..255
        return stored;
    }

    public static void main(String[] args) {
        System.out.println("CO04 Target too small (Java)");
        System.out.println("  BUG 30000 + 10000 -> " + totalBuggy());
        System.out.println("  FIX 30000 + 10000 -> " + totalFixed());
        System.out.println("  BUG brightness 200 -> " + brightnessBuggy(200));
        System.out.println("  FIX brightness 200 -> " + brightnessFixed(200));
    }
}
