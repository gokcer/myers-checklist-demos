"""IO02 · Input/output · Are the attributes of the open() correct? ("w" overwrites, "a" appends)"""
import os
import tempfile


def read_all(path: str) -> str:
    with open(path, encoding="utf-8") as f:
        return f.read().strip().replace("\n", " | ")


def log_buggy(path: str) -> str:
    for event in ["login", "logout"]:
        with open(path, "w", encoding="utf-8") as f:      # BUG: "w" empties the file each time
            f.write(event + "\n")
    return read_all(path)


def log_fixed(path: str) -> str:
    for event in ["login", "logout"]:
        with open(path, "a", encoding="utf-8") as f:      # FIX: "a" appends
            f.write(event + "\n")
    return read_all(path)


if __name__ == "__main__":
    print("IO02 Open mode: overwrite instead of append (Python)   events: login, logout")
    folder = tempfile.mkdtemp()
    print("  BUG ->", log_buggy(os.path.join(folder, "a.log")))
    print("  FIX ->", log_fixed(os.path.join(folder, "b.log")))
