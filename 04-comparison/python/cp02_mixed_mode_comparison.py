"""CP02 · Comparison · Mixed-mode comparisons: Decimal vs float."""
from decimal import Decimal


def is_ten_percent_buggy(rate: Decimal) -> bool:
    return rate == 0.1                         # BUG: Decimal("0.1") is exact, float 0.1 is not


def is_ten_percent_fixed(rate: Decimal) -> bool:
    return rate == Decimal("0.1")              # FIX: compare values of the same type


if __name__ == "__main__":
    print('CP02 Mixed-mode comparison (Python)   rate = Decimal("0.1")')
    print("  BUG ->", is_ten_percent_buggy(Decimal("0.1")))
    print("  FIX ->", is_ten_percent_fixed(Decimal("0.1")))
