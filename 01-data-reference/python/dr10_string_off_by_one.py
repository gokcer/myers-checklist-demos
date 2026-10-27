"""DR10 · Data reference · Are string index limits off by one?"""


# spec: return the file extension WITHOUT the dot: "report.pdf" -> "pdf"
def extension_buggy(file: str) -> str:
    start = file.rindex(".")              # BUG: starts at the dot -> ".pdf"
    return file[start:]


def extension_fixed(file: str) -> str:
    start = file.rindex(".") + 1          # FIX: one character after the dot
    return file[start:]


# spec: remove the first and the last character: '"hello"' -> 'hello'
def strip_quotes_buggy(s: str) -> str:
    end = len(s)                          # BUG: the slice end is excluded, so len(s) keeps the last quote
    return s[1:end]


def strip_quotes_fixed(s: str) -> str:
    end = len(s) - 1                      # FIX
    return s[1:end]


if __name__ == "__main__":
    print("DR10 String index off by one (Python)")
    print("  BUG extension ->", repr(extension_buggy("report.pdf")))
    print("  FIX extension ->", repr(extension_fixed("report.pdf")))
    print("  BUG strip quotes ->", repr(strip_quotes_buggy('"hello"')))
    print("  FIX strip quotes ->", repr(strip_quotes_fixed('"hello"')))
