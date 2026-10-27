"""IO09 · Input/output · Does the program handle "file not found" properly?"""

CONFIG = "settings-that-are-not-there.ini"


def read_first_line(path: str) -> str:
    with open(path, encoding="utf-8") as f:
        return f.readline().strip()


def theme_buggy() -> str:
    theme = read_first_line(CONFIG)                  # BUG: a missing file -> a crash with a traceback
    return theme


def theme_fixed() -> str:
    try:
        theme = read_first_line(CONFIG)
    except FileNotFoundError:
        theme = "light (default, no settings file)"  # FIX: the spec's fallback
    return theme


if __name__ == "__main__":
    print("IO09 File not found (Python)")
    try:
        print("  BUG ->", theme_buggy())
    except FileNotFoundError as e:
        print("  BUG -> FileNotFoundError:", e)
    print("  FIX ->", theme_fixed())
