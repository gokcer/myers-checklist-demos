"""CP04 · Comparison · Does each Boolean expression say what it should? (and, or, not)"""


def is_weekday_buggy(day: str) -> bool:
    return day != "Sat" or day != "Sun"        # BUG: always True


def is_weekday_fixed(day: str) -> bool:
    return day != "Sat" and day != "Sun"       # FIX: De Morgan: not(a or b) = not a and not b


if __name__ == "__main__":
    print("CP04 Boolean logic (Python)   Sunday")
    print("  BUG ->", is_weekday_buggy("Sun"))
    print("  FIX ->", is_weekday_fixed("Sun"))
