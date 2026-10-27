// OT02 · Other · Did any variable or literal get an unexpected default attribute?
// Myers: check the compiler's attribute listing. In Java: integer literals are int; a leading 0 means octal.
public class OT02UnexpectedDefaults {

    static int roomCodeBuggy() {
        int code = 010;              // BUG: a leading 0 makes it octal: 8
        return code;
    }

    static int roomCodeFixed() {
        int code = 10;               // FIX: no leading zero (keep codes like "010" as text)
        return code;
    }

    static long maskBuggy() {
        long mask = 1 << 40;         // BUG: 1 is an int literal; the shift happens in 32 bits -> 256
        return mask;
    }

    static long maskFixed() {
        long mask = 1L << 40;        // FIX: 1L is a long literal
        return mask;
    }

    public static void main(String[] args) {
        System.out.println("OT02 Unexpected default attributes (Java)");
        System.out.println("  BUG room code 010 -> " + roomCodeBuggy());
        System.out.println("  FIX room code 10 -> " + roomCodeFixed());
        System.out.println("  BUG 1 << 40 -> " + maskBuggy());
        System.out.println("  FIX 1L << 40 -> " + maskFixed());
    }
}
