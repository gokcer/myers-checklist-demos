"""OT03 · Other · Did the interpreter print warnings? Read every one.

Run:  python -W error::SyntaxWarning ot03_compiler_warnings.py   to turn them into errors.
Python prints both SyntaxWarnings below when it compiles this file, before anything runs.
"""


def check_buggy(balance: int) -> str:
    assert (balance >= 0, "balance must not be negative")   # BUG: a non-empty tuple is always true
    return "ok"


def check_fixed(balance: int) -> str:
    assert balance >= 0, "balance must not be negative"     # FIX: no parentheses
    return "ok"


def is_gold_buggy(level: str) -> bool:
    return level is "gold"                                  # BUG: identity ("same object"), not equality


def is_gold_fixed(level: str) -> bool:
    return level == "gold"                                  # FIX: compare the values


if __name__ == "__main__":
    print("OT03 Warnings that were ignored (Python)   balance -5; a level 'gold' read from input")
    print("  BUG ->", check_buggy(-5))
    try:
        print("  FIX ->", check_fixed(-5))
    except AssertionError as e:
        print("  FIX -> AssertionError:", e)
    level = "".join(["go", "ld"])                           # built at run time, like text from input()
    print("  BUG is the level gold? ->", is_gold_buggy(level))
    print("  FIX is the level gold? ->", is_gold_fixed(level))
