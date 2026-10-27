"""OT02 · Other · Did anything get an unexpected default?

A function that falls off the end returns None by default. On one path the return was forgotten.
"""


def grade_buggy(score: int) -> str:
    if score >= 90:
        return "A"
    elif score >= 50:
        return "pass"
    # BUG: no return for score < 50 -> the default None


def grade_fixed(score: int) -> str:
    if score >= 90:
        return "A"
    elif score >= 50:
        return "pass"
    return "fail"                         # FIX: the missing path returns a value too


if __name__ == "__main__":
    print("OT02 Unexpected default: implicit None (Python)   score 30")
    print("  BUG ->", grade_buggy(30))
    print("  FIX ->", grade_fixed(30))
