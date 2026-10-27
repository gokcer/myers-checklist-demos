// DR07 · Data reference · Memory units: is a small unit (a byte) read the way the code assumes?
// Java's byte is signed: 0xC8 (200) is stored as -56. Code that reads binary data often forgets this.
public class DR07SignedByte {

    static final byte[] PACKET = {(byte) 200, 10};   // first byte: a length of 200

    static int lengthBuggy() {
        int length = PACKET[0];             // BUG: -56
        return length;
    }

    static int lengthFixed() {
        int length = PACKET[0] & 0xFF;      // FIX: read it as an unsigned value
        return length;
    }

    public static void main(String[] args) {
        System.out.println("DR07 Signed byte (Java)   expected 200");
        System.out.println("  BUG -> " + lengthBuggy());
        System.out.println("  FIX -> " + lengthFixed());
    }
}
