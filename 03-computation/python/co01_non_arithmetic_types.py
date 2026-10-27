"""CO01 · Computation · Is a computation done on values of a non-arithmetic type?

input() and files give text. "+" on text joins it, "*" repeats it. No error appears.
"""


def total_buggy(a: str, b: str) -> str:
    return a + b                       # BUG: "40" + "60" == "4060"


def total_fixed(a: str, b: str) -> int:
    return int(a) + int(b)             # FIX: convert the text to numbers once, at the border


def double_buggy(quantity: str) -> str:
    return quantity * 2                # BUG: "3" * 2 == "33"


def double_fixed(quantity: str) -> int:
    return int(quantity) * 2           # FIX


if __name__ == "__main__":
    print("CO01 Computation with non-arithmetic types (Python)   values read as text: '40', '60', '3'")
    print("  BUG total ->", repr(total_buggy("40", "60")))
    print("  FIX total ->", repr(total_fixed("40", "60")))
    print("  BUG double the quantity ->", repr(double_buggy("3")))
    print("  FIX double the quantity ->", repr(double_fixed("3")))
