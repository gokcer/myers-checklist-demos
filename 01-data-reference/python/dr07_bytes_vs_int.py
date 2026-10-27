"""DR07 · Data reference · Memory units: is a byte read the way the code assumes?

Indexing a bytes object gives an int; slicing gives bytes. Comparing an int with b"A" is always False.
"""

PACKET = b"ACK:200"


def is_ack_buggy(packet: bytes) -> bool:
    first = packet[0]                  # BUG: packet[0] is the int 65, not b"A"
    return first == b"A"


def is_ack_fixed(packet: bytes) -> bool:
    first = packet[0:1]                # FIX: a slice is bytes (or compare packet[0] with ord("A"))
    return first == b"A"


if __name__ == "__main__":
    print("DR07 Byte vs int (Python)   packet b'ACK:200', expected True")
    print("  BUG ->", is_ack_buggy(PACKET))
    print("  FIX ->", is_ack_fixed(PACKET))
