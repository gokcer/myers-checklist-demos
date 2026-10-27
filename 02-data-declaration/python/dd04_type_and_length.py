"""DD04 · Data declaration · Does each variable have the right type?

Python ints never overflow, but float is still the wrong type for money.
"""
from decimal import Decimal


def total_buggy(prices: list[str]) -> float:
    total = 0.0
    for p in prices:
        total += float(p)              # BUG: binary floating point for money
    return total


def total_fixed(prices: list[str]) -> Decimal:
    total = Decimal("0")
    for p in prices:
        total += Decimal(p)            # FIX: a decimal type for money
    return total


if __name__ == "__main__":
    print("DD04 Wrong type for money (Python)   0.10 + 0.20, expected 0.30")
    print("  BUG ->", total_buggy(["0.10", "0.20"]))
    print("  FIX ->", total_fixed(["0.10", "0.20"]))
