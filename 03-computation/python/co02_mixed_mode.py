"""CO02 · Computation · Mixed-mode computations: int and float together.

int(-7 / 2) truncates toward zero (-3); -7 // 2 floors (-4). Code that mixes the two gets different answers.
"""


# spec: which half-hour slot is a time in? (minutes, negative = before the start; always round down)
def half_hours_buggy(minutes: int) -> int:
    return int(minutes / 30)        # BUG: float division, then int() truncates toward zero: -75 -> -2


def half_hours_fixed(minutes: int) -> int:
    return minutes // 30            # FIX: integer floor division: -75 -> -3


if __name__ == "__main__":
    print("CO02 Mixed-mode computation (Python)   -75 minutes")
    print("  BUG half-hour slot ->", half_hours_buggy(-75))
    print("  FIX half-hour slot ->", half_hours_fixed(-75))
