"""DR05 · Data reference · Is the same data reached through two names (aliases)?

`backup = scores` does not copy the list. It gives the SAME list a second name.
"""


def curve_buggy(scores: list[int]) -> tuple[list[int], list[int]]:
    backup = scores                    # BUG: an alias, not a copy
    for i in range(len(scores)):
        scores[i] += 10
    return backup, scores              # the "backup" changed too


def curve_fixed(scores: list[int]) -> tuple[list[int], list[int]]:
    backup = list(scores)              # FIX: a real copy
    for i in range(len(scores)):
        scores[i] += 10
    return backup, scores


if __name__ == "__main__":
    print("DR05 Alias (Python)   scores [50, 60]; result is (backup, curved)")
    print("  BUG ->", curve_buggy([50, 60]))
    print("  FIX ->", curve_fixed([50, 60]))
