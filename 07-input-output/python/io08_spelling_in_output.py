"""IO08 · Input/output · Are there spelling or grammar mistakes in the text the program shows?"""


def message_buggy(n: int) -> str:
    word = "files"
    return "Sucessfully deleted " + str(n) + " " + word       # BUG: a typo, and "1 files"


def message_fixed(n: int) -> str:
    word = "file" if n == 1 else "files"
    return "Successfully deleted " + str(n) + " " + word      # FIX


if __name__ == "__main__":
    print("IO08 Spelling and grammar in output (Python)   1 file")
    print("  BUG ->", message_buggy(1))
    print("  FIX ->", message_fixed(1))
