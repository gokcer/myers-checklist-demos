"""IO04 · Input/output · Is every file open when it is used? (and: is a huge file read into memory at once?)"""
import os
import tempfile

PATH = os.path.join(tempfile.mkdtemp(), "report.csv")


def write_buggy() -> str:
    with open(PATH, "w", encoding="utf-8") as f:
        f.write("header\n")
    f.write("row 1\n")                                  # BUG: outside the with block the file is closed
    return "written"


def write_fixed() -> str:
    with open(PATH, "w", encoding="utf-8") as f:
        f.write("header\n")
        f.write("row 1\n")                              # FIX: every write inside the block
    return "written"


# big files: read one line at a time; f.read() would load the whole file into memory
def count_lines() -> int:
    count = 0
    with open(PATH, encoding="utf-8") as f:
        for _ in f:
            count += 1
    return count


if __name__ == "__main__":
    print("IO04 File used after it was closed (Python)")
    try:
        print("  BUG ->", write_buggy())
    except ValueError as e:
        print("  BUG -> ValueError:", e)
    print("  FIX ->", write_fixed())
    print("  lines in the file (read line by line) ->", count_lines())
