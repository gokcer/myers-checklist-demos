"""CO07 · Computation · Base-2 inaccuracy: 10 x 0.1 is rarely exactly 1.0."""
from decimal import Decimal


def sum_buggy() -> float:
    total = 0.0
    for _ in range(10):
        total = total + 0.1                  # BUG: 0.1 has no exact binary form
    return total


def sum_fixed() -> Decimal:
    total = Decimal("0")
    for _ in range(10):
        total = total + Decimal("0.1")       # FIX: Decimal is exact for 0.1 (math.fsum also helps)
    return total


if __name__ == "__main__":
    print("CO07 Base-2 inaccuracy (Python)   10 x 0.1")
    print("  BUG ->", sum_buggy())
    print("  FIX ->", sum_fixed())
