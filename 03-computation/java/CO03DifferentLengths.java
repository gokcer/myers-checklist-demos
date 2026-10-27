// CO03 · Computation · Same data type, different lengths (int and long).
// The multiplication is done in 32-bit int and overflows; only the wrong result is widened to long.
public class CO03DifferentLengths {

    static long bytesBuggy(int megabytes) {
        long bytes = megabytes * 1024 * 1024;          // BUG: int * int * int, then stored in a long
        return bytes;
    }

    static long bytesFixed(int megabytes) {
        long bytes = (long) megabytes * 1024 * 1024;   // FIX: widen BEFORE multiplying
        return bytes;
    }

    public static void main(String[] args) {
        System.out.println("CO03 Different lengths: int vs long (Java)   4096 MB");
        System.out.println("  BUG -> " + bytesBuggy(4096));
        System.out.println("  FIX -> " + bytesFixed(4096));
    }
}
