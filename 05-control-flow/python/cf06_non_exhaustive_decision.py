"""CF06 · Control flow · Are there non-exhaustive decisions? ("not card or cash, so it is voucher")"""


def fee_buggy(method: str) -> float:
    if method == "card":
        fee = 2.0
    elif method == "cash":
        fee = 0.0
    else:
        fee = 1.0                         # BUG: assumes "voucher"; "Card" or "bitcoin" land here too
    return fee


def fee_fixed(method: str) -> float:
    if method == "card":
        fee = 2.0
    elif method == "cash":
        fee = 0.0
    elif method == "voucher":             # FIX: name the last case too ...
        fee = 1.0
    else:                                 # ... and reject values the spec does not allow
        raise ValueError("unknown payment method: " + method)
    return fee


if __name__ == "__main__":
    print('CF06 Non-exhaustive decision (Python)   method = "Card"')
    print("  BUG ->", fee_buggy("Card"))
    try:
        print("  FIX ->", fee_fixed("Card"))
    except ValueError as e:
        print("  FIX -> ValueError:", e)
