"""OT01 · Other · Are there variables that are never used, or used only once?

A value that is computed but never used often means a missing step.
"""


def total_buggy(price: float, member: bool) -> float:
    discount = 0.10 if member else 0.0      # BUG: assigned, never used
    total = price
    return total


def total_fixed(price: float, member: bool) -> float:
    discount = 0.10 if member else 0.0
    total = price * (1 - discount)          # FIX: the missing step
    return total


if __name__ == "__main__":
    print("OT01 Variable never used (Python)   member, price 100")
    print("  BUG ->", total_buggy(100, True))
    print("  FIX ->", total_fixed(100, True))
