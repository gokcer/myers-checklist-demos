"""IO01 · Input/output · Are the declared attributes of the file correct? (the character encoding)

open() without encoding= uses the operating system default (often cp1252 on Windows).
Here we make the wrong choice explicit so the demo behaves the same on every computer.
"""
import os
import tempfile

PATH = os.path.join(tempfile.mkdtemp(), "names.txt")


def make_sample_file() -> None:
    with open(PATH, "w", encoding="utf-8") as f:              # the file is UTF-8
        f.write("Gökçer, İzmir, ğüşıö")


def read_buggy() -> str:
    with open(PATH, encoding="latin-1") as f:                  # BUG: the wrong encoding (like a wrong OS default)
        return f.read()


def read_fixed() -> str:
    with open(PATH, encoding="utf-8") as f:                    # FIX: always say encoding="utf-8"
        return f.read()


if __name__ == "__main__":
    print("IO01 File encoding (Python)   the file is UTF-8")
    make_sample_file()
    print("  BUG ->", read_buggy())
    print("  FIX ->", read_fixed())
