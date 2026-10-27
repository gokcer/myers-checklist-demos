"""CO03 · Computation · Same type, different lengths.

Python's int has no limit, but fixed-size storage does: array('h') holds 16-bit integers.
"""
from array import array


def store_buggy(values: list[int]) -> list[int]:
    stored = array("h", values)        # BUG: "h" is 16-bit; 40000 does not fit
    return stored.tolist()


def store_fixed(values: list[int]) -> list[int]:
    stored = array("l", values)        # FIX: a type long enough for the data
    return stored.tolist()


if __name__ == "__main__":
    print("CO03 Different lengths (Python)   store [100, 40000]")
    try:
        print("  BUG ->", store_buggy([100, 40000]))
    except OverflowError as e:
        print("  BUG -> OverflowError:", e)
    print("  FIX ->", store_fixed([100, 40000]))
