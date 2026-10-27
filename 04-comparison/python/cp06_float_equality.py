"""CP06 · Comparison · Are floating-point numbers compared for exact equality?"""


def paid_in_full_buggy(paid: float, price: float) -> bool:
    return paid == price                         # BUG: 0.1 + 0.2 is not 0.3


def paid_in_full_fixed(paid: float, price: float) -> bool:
    return abs(paid - price) < 1e-9              # FIX: compare with a tolerance (or use integer cents)


if __name__ == "__main__":
    print("CP06 Floating-point equality (Python)   paid 0.1 + 0.2 for a price of 0.3")
    print("  BUG ->", paid_in_full_buggy(0.1 + 0.2, 0.3))
    print("  FIX ->", paid_in_full_fixed(0.1 + 0.2, 0.3))
