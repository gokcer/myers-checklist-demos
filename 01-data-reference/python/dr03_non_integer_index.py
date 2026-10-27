"""DR03 · Data reference · Does every index have an integer value?

Python 3 refuses a float index (good), so programmers convert with int(), which truncates.
"""

LABEL = [str(k) + " kuruş" for k in range(100)]     # LABEL[k] is the label for k kuruş


def middle_buggy(items: list[str]) -> str:
    index = len(items) / 2                 # BUG: in Python 3, / always gives a float
    return items[index]


def middle_fixed(items: list[str]) -> str:
    index = len(items) // 2                # FIX: integer division
    return items[index]


def label_buggy(price_lira: float) -> str:
    index = int(price_lira * 100)          # BUG: 0.29 * 100 == 28.999999999999996 -> 28
    return LABEL[index]


def label_fixed(price_lira: float) -> str:
    index = round(price_lira * 100)        # FIX: round first (or store money as integer kuruş)
    return LABEL[index]


if __name__ == "__main__":
    print("DR03 Non-integer index (Python)")
    try:
        print("  BUG middle of [a, b, c] ->", middle_buggy(["a", "b", "c"]))
    except TypeError as e:
        print("  BUG middle of [a, b, c] -> TypeError:", e)
    print("  FIX middle of [a, b, c] ->", middle_fixed(["a", "b", "c"]))
    print("  BUG label for 0.29 lira ->", label_buggy(0.29))
    print("  FIX label for 0.29 lira ->", label_fixed(0.29))
