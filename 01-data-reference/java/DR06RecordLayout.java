// DR06 · Data reference · Does the data really have the type/layout the code expects?
// A sensor writes a 4-byte integer in little-endian order. ByteBuffer reads big-endian by default.
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DR06RecordLayout {

    // the record as the device wrote it: value 1000, little-endian
    static final byte[] RECORD = {(byte) 0xE8, 0x03, 0x00, 0x00};

    static int readBuggy() {
        ByteBuffer buffer = ByteBuffer.wrap(RECORD);           // BUG: assumes big-endian (the default)
        return buffer.getInt();
    }

    static int readFixed() {
        ByteBuffer buffer = ByteBuffer.wrap(RECORD);
        buffer.order(ByteOrder.LITTLE_ENDIAN);                 // FIX: state the layout the device uses
        return buffer.getInt();
    }

    public static void main(String[] args) {
        System.out.println("DR06 Record layout differs from what the code expects (Java)   expected 1000");
        System.out.println("  BUG -> " + readBuggy());
        System.out.println("  FIX -> " + readFixed());
    }
}
