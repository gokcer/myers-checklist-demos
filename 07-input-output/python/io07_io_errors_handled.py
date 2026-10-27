"""IO07 · Input/output · Are I/O error conditions handled correctly (not hidden)?"""

TARGET = "this-folder-does-not-exist/backup.txt"


def write_file(path: str, text: str) -> None:
    with open(path, "w", encoding="utf-8") as f:
        f.write(text)


def backup_buggy() -> str:
    status = "backup done"
    try:
        write_file(TARGET, "data")
    except OSError:
        pass                                      # BUG: error swallowed; the user is told it worked
    return status


def backup_fixed() -> str:
    status = "backup done"
    try:
        write_file(TARGET, "data")
    except OSError as e:
        status = "backup FAILED: " + str(e)       # FIX: report it (or let it propagate)
    return status


if __name__ == "__main__":
    print("IO07 I/O error hidden (Python)   the folder does not exist")
    print("  BUG ->", backup_buggy())
    print("  FIX ->", backup_fixed())
