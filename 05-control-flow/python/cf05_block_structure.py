"""CF05 · Control flow · Does each block start and end where it should?

In Python the block is the indentation. One wrong dedent moves a line out of the if.
"""


def withdraw_buggy(balance: int, amount: int) -> str:
    if amount > balance:
        print("    (log) insufficient funds")
    return "REJECTED"                     # BUG: one level too far left -> always runs
    return "OK, new balance " + str(balance - amount)


def withdraw_fixed(balance: int, amount: int) -> str:
    if amount > balance:
        print("    (log) insufficient funds")
        return "REJECTED"                 # FIX: inside the if
    return "OK, new balance " + str(balance - amount)


if __name__ == "__main__":
    print("CF05 Block structure (Python)   balance 100, withdraw 30")
    print("  BUG ->", withdraw_buggy(100, 30))
    print("  FIX ->", withdraw_fixed(100, 30))
