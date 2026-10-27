"""CO10 · Computation · Are there invalid uses of integer arithmetic, especially division?"""


def percent_buggy(done: int, total: int) -> int:
    return done // total * 100        # BUG: 45 // 60 == 0


def percent_fixed(done: int, total: int) -> int:
    return done * 100 // total        # FIX: multiply first


def double_then_halve(i: int) -> int:
    return 2 * i // 2                 # (2 * 7) // 2 = 7


def halve_then_double(i: int) -> int:
    return 2 * (i // 2)               # 2 * (7 // 2) = 2 * 3 = 6


if __name__ == "__main__":
    print("CO10 Integer arithmetic (Python)")
    print("  BUG percent 45 of 60 ->", percent_buggy(45, 60))
    print("  FIX percent 45 of 60 ->", percent_fixed(45, 60))
    print("  Myers 2 * i // 2   (i = 7) ->", double_then_halve(7))
    print("  Myers 2 * (i // 2) (i = 7) ->", halve_then_double(7))
