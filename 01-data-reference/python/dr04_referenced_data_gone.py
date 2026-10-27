"""DR04 · Data reference · Does the data a reference points to still exist?

Python has no dangling pointers, but an iterator or generator is used up after one pass.
The second pass sees nothing, and there is no error at all.
"""

ORDERS = [120, 45, 300]


def summary_buggy() -> str:
    big = (o for o in ORDERS if o > 100)   # BUG: a generator: its data can be read only once
    count = 0
    for _ in big:
        count += 1
    total = 0
    for order in big:                      # the generator is already empty -> total stays 0
        total += order
    return str(count) + " big orders, total " + str(total)


def summary_fixed() -> str:
    big = [o for o in ORDERS if o > 100]   # FIX: a list keeps its data for every pass
    count = 0
    for _ in big:
        count += 1
    total = 0
    for order in big:
        total += order
    return str(count) + " big orders, total " + str(total)


if __name__ == "__main__":
    print("DR04 Referenced data no longer exists (Python)")
    print("  BUG ->", summary_buggy())
    print("  FIX ->", summary_fixed())
