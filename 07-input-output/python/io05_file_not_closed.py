"""IO05 · Input/output · Is every file closed after use?

Written data waits in a buffer. Until the file is flushed or closed, a reader may see nothing.
"""
import os
import tempfile

FOLDER = tempfile.mkdtemp()


def read_all(path: str) -> str:
    with open(path, encoding="utf-8") as f:
        return f.read()


def save_buggy() -> str:
    path = os.path.join(FOLDER, "a.txt")
    f = open(path, "w", encoding="utf-8")           # BUG: the file is never closed
    f.write("order 1001: paid")
    return "file now contains: " + repr(read_all(path))


def save_fixed() -> str:
    path = os.path.join(FOLDER, "b.txt")
    with open(path, "w", encoding="utf-8") as f:    # FIX: "with" closes (and flushes) the file
        f.write("order 1001: paid")
    return "file now contains: " + repr(read_all(path))


if __name__ == "__main__":
    print("IO05 File not closed (Python)   what does the file contain right after saving?")
    print("  BUG ->", save_buggy())
    print("  FIX ->", save_fixed())
