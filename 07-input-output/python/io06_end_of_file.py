"""IO06 · Input/output · Is the end-of-file condition detected and handled correctly?

readline() returns "" at end-of-file, but a blank line is "\n". Mixing them up stops too early.
"""
import io

FILE = "10\n20\n\n30\n"         # a blank line in the middle


def sum_buggy() -> int:
    f = io.StringIO(FILE)
    total = 0
    while True:
        line = f.readline()
        if not line.strip():    # BUG: treats the blank line as end-of-file
            break
        total += int(line)
    return total


def sum_fixed() -> int:
    f = io.StringIO(FILE)
    total = 0
    while True:
        line = f.readline()
        if not line:            # FIX: only "" is end-of-file ...
            break
        if line.strip():        # ... a blank line ("\n") is just skipped
            total += int(line)
    return total


if __name__ == "__main__":
    print("IO06 End of file (Python)   lines 10, 20, (blank), 30: expected 60")
    print("  BUG ->", sum_buggy())
    print("  FIX ->", sum_fixed())
