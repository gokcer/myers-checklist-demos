"""CP03 · Comparison · Is the comparison operator correct? ("at most", "at least")"""

# spec: a basket may hold AT MOST 3 discounted items


def can_add_buggy(items_in_basket: int) -> bool:
    return items_in_basket <= 3          # BUG: lets the 4th item in (3 in basket, 3 <= 3)


def can_add_fixed(items_in_basket: int) -> bool:
    return items_in_basket < 3           # FIX: after adding, the count must still be <= 3


if __name__ == "__main__":
    print("CP03 Wrong comparison operator (Python)   basket already has 3 items")
    print("  BUG can add a 4th? ->", can_add_buggy(3))
    print("  FIX can add a 4th? ->", can_add_fixed(3))
