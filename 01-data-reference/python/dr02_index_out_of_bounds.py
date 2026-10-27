"""DR02 · Data reference · Is every index inside the bounds of the list?

Python adds a trap: a negative index does NOT fail, it silently counts from the end.
"""

SCORES = [70, 85, 90]


def last_score_buggy() -> int:
    return SCORES[len(SCORES)]               # BUG: valid indexes are 0 .. len-1


def last_score_fixed() -> int:
    return SCORES[len(SCORES) - 1]           # FIX: the last index is len-1 (or simply SCORES[-1])


def previous_score_buggy(i: int) -> int | None:
    return SCORES[i - 1]                     # BUG: for i == 0 this reads SCORES[-1], the LAST score


def previous_score_fixed(i: int) -> int | None:
    return SCORES[i - 1] if i > 0 else None  # FIX: check the bound before indexing


if __name__ == "__main__":
    print("DR02 Index out of bounds (Python)")
    try:
        print("  BUG last score ->", last_score_buggy())
    except IndexError as e:
        print("  BUG last score -> IndexError:", e)
    print("  FIX last score ->", last_score_fixed())
    print("  BUG score before the first ->", previous_score_buggy(0))
    print("  FIX score before the first ->", previous_score_fixed(0))
