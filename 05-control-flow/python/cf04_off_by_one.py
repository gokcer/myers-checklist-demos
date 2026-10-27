"""CF04 · Control flow · Are there off-by-one errors?"""


def days_in_range_buggy(first: int, last: int) -> list[int]:
    return list(range(first, last))          # BUG: range() excludes the end; day 'last' is missing


def days_in_range_fixed(first: int, last: int) -> list[int]:
    return list(range(first, last + 1))      # FIX: include the end


if __name__ == "__main__":
    print("CF04 Off by one (Python)   days 1 to 5 inclusive")
    print("  BUG ->", days_in_range_buggy(1, 5))
    print("  FIX ->", days_in_range_fixed(1, 5))
