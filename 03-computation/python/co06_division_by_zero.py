"""CO06 · Computation · Can the divisor be zero?"""


def average_buggy(values: list[float]) -> float:
    return sum(values) / len(values)                       # BUG: empty list -> ZeroDivisionError


def average_fixed(values: list[float]) -> float:
    return sum(values) / len(values) if values else 0.0    # FIX: the spec decides what "no data" means


if __name__ == "__main__":
    print("CO06 Division by zero (Python)   empty list")
    try:
        print("  BUG ->", average_buggy([]))
    except ZeroDivisionError as e:
        print("  BUG -> ZeroDivisionError:", e)
    print("  FIX ->", average_fixed([]))
