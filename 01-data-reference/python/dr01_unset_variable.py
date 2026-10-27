"""DR01 · Data reference · Is a variable used before it gets a value?"""


def shipping_buggy(weight_kg: float) -> int:
    if weight_kg < 5:
        fee = 20
    elif weight_kg > 5:                 # BUG: exactly 5 kg is on neither path, so `fee` never gets a value
        fee = 35
    return fee


def shipping_fixed(weight_kg: float) -> int:
    if weight_kg <= 5:
        fee = 20
    else:                               # FIX: an else gives `fee` a value on every path
        fee = 35
    return fee


if __name__ == "__main__":
    print("DR01 Unset variable (Python)   weight = 5 kg")
    try:
        print("  BUG ->", shipping_buggy(5))
    except UnboundLocalError as e:
        print("  BUG -> UnboundLocalError:", e)
    print("  FIX ->", shipping_fixed(5))
