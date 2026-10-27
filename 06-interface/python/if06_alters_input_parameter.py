"""IF06 · Interface · Does a function change a parameter that should only be an input?"""


def median_buggy(values: list[int]) -> int:
    ordered = values                    # BUG: the caller's list, not a copy
    ordered.sort()
    return ordered[len(ordered) // 2]


def median_fixed(values: list[int]) -> int:
    ordered = list(values)              # FIX: sort a copy
    ordered.sort()
    return ordered[len(ordered) // 2]


if __name__ == "__main__":
    print("IF06 Function alters an input parameter (Python)   arrival order [30, 10, 20]")
    arrivals = [30, 10, 20]
    median = median_buggy(arrivals)
    print("  BUG -> median", median, "arrivals now", arrivals)
    arrivals = [30, 10, 20]
    median = median_fixed(arrivals)
    print("  FIX -> median", median, "arrivals now", arrivals)
