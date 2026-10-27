"""CO08 · Computation · Can a value go outside its meaningful range?"""


def win_probability_buggy(wins: int, losses: int, bonus: float) -> float:
    p = wins / (wins + losses) + bonus       # BUG: a probability can now be greater than 1.0
    return p


def win_probability_fixed(wins: int, losses: int, bonus: float) -> float:
    p = wins / (wins + losses) + bonus
    p = min(max(p, 0.0), 1.0)                # FIX: keep it in [0, 1] (or reject the input)
    return p


if __name__ == "__main__":
    print("CO08 Value outside its meaningful range (Python)   9 wins, 1 loss, bonus 0.2")
    print("  BUG ->", win_probability_buggy(9, 1, 0.2))
    print("  FIX ->", win_probability_fixed(9, 1, 0.2))
