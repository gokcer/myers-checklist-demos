"""DR06 · Data reference · Does the data really have the layout the code expects?

A sensor writes a 4-byte unsigned integer in little-endian order.
"""
import struct

RECORD = bytes([0xE8, 0x03, 0x00, 0x00])   # value 1000, little-endian


def read_buggy() -> int:
    layout = ">I"                          # BUG: ">" means big-endian
    return struct.unpack(layout, RECORD)[0]


def read_fixed() -> int:
    layout = "<I"                          # FIX: "<" little-endian, as the device writes it
    return struct.unpack(layout, RECORD)[0]


if __name__ == "__main__":
    print("DR06 Record layout (Python)   expected 1000")
    print("  BUG ->", read_buggy())
    print("  FIX ->", read_fixed())
