"""OT04 · Other · Is the module robust: does it check its input for validity?"""


def total_buggy(unit_price: int, quantity_text: str) -> int:
    quantity = int(quantity_text)
    # BUG: the quantity is not checked; "-3" is accepted
    return unit_price * quantity


def total_fixed(unit_price: int, quantity_text: str) -> int:
    quantity = int(quantity_text)
    if not 1 <= quantity <= 99:                            # FIX: validate at the border
        raise ValueError("quantity must be 1..99, got " + str(quantity))
    return unit_price * quantity


if __name__ == "__main__":
    print('OT04 Robustness: input not checked (Python)   price 50, quantity "-3"')
    print("  BUG ->", total_buggy(50, "-3"))
    try:
        print("  FIX ->", total_fixed(50, "-3"))
    except ValueError as e:
        print("  FIX -> ValueError:", e)
