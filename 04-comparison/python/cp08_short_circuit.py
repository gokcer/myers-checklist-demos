"""CP08 · Comparison · Does the way the Boolean expression is evaluated affect the program?

"and" stops early. "&" on two bools does not: both sides run.
"""


def fast_buggy(distance: int, minutes: int) -> bool:
    return (minutes != 0) & (distance / minutes > 2)     # BUG: & runs the right side even when minutes == 0


def fast_fixed(distance: int, minutes: int) -> bool:
    return (minutes != 0) and (distance / minutes > 2)   # FIX: "and" stops when the left side is False


if __name__ == "__main__":
    print("CP08 Short-circuit evaluation (Python)   distance 10, minutes 0")
    try:
        print("  BUG ->", fast_buggy(10, 0))
    except ZeroDivisionError as e:
        print("  BUG -> ZeroDivisionError:", e)
    print("  FIX ->", fast_fixed(10, 0))
