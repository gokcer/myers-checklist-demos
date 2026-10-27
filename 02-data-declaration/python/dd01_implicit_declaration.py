"""DD01 · Data declaration · Are all variables declared explicitly?

Python has no declarations: a typo silently creates a NEW variable.
"""


def total_buggy(prices: list[float]) -> float:
    total = 0.0
    for p in prices:
        totl = total + p        # BUG: a typo creates a new variable; total stays 0
    return total


def total_fixed(prices: list[float]) -> float:
    total = 0.0
    for p in prices:
        total = total + p       # FIX: the name that was meant
    return total


if __name__ == "__main__":
    print("DD01 Implicit declaration by typo (Python)   prices 10 and 20, expected 30.0")
    print("  BUG ->", total_buggy([10.0, 20.0]))
    print("  FIX ->", total_fixed([10.0, 20.0]))
