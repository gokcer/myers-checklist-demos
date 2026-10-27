"""CF01 · Control flow · In a multiway branch, can the selector take a value that has no branch?

A dict used as a "switch" with .get() returns None for an unknown key, and the error appears much later.
"""

FEES = {1: 20, 2: 35, 3: 50}


def total_buggy(zone: int, price: int) -> int:
    fee = FEES.get(zone)                 # BUG: zone 4 has no entry -> None
    return price + fee                   # TypeError here, far from the real cause


def total_fixed(zone: int, price: int) -> int:
    if zone not in FEES:                 # FIX: reject a value that has no branch, right here
        raise ValueError("unknown zone " + str(zone))
    fee = FEES[zone]
    return price + fee


if __name__ == "__main__":
    print("CF01 Multiway branch (Python)   zone 4, price 100")
    try:
        print("  BUG ->", total_buggy(4, 100))
    except TypeError as e:
        print("  BUG -> TypeError:", e)
    try:
        print("  FIX ->", total_fixed(4, 100))
    except ValueError as e:
        print("  FIX -> ValueError:", e)
