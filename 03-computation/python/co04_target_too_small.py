"""CO04 · Computation · Is the target of an assignment smaller than the result?

ctypes (used to talk to C libraries and hardware) cuts values silently.
"""
import ctypes


def brightness_buggy(level: int) -> int:
    stored = ctypes.c_uint8(level)         # BUG: 300 does not fit into 8 bits -> 44
    return stored.value


def brightness_fixed(level: int) -> int:
    if not 0 <= level <= 255:              # FIX: check the range before storing
        raise ValueError("brightness must be 0..255, got " + str(level))
    stored = ctypes.c_uint8(level)
    return stored.value


if __name__ == "__main__":
    print("CO04 Target too small (Python)   brightness 300")
    print("  BUG ->", brightness_buggy(300))
    try:
        print("  FIX ->", brightness_fixed(300))
    except ValueError as e:
        print("  FIX -> ValueError:", e)
