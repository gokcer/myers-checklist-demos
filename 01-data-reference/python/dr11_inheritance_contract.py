"""DR11 · Data reference · Does the subclass meet every inheritance requirement?

A subclass that defines __init__ must call the parent's __init__, or the parent's fields never exist.
"""


class Account:
    def __init__(self, owner: str) -> None:
        self.owner = owner
        self.balance = 0


class SavingsBuggy(Account):
    def __init__(self, owner: str, rate: float) -> None:
        # BUG: Account.__init__ is never called, so owner and balance do not exist
        self.rate = rate


class SavingsFixed(Account):
    def __init__(self, owner: str, rate: float) -> None:
        super().__init__(owner)           # FIX: let the parent set its own fields
        self.rate = rate


if __name__ == "__main__":
    print("DR11 Inheritance requirement: call super().__init__ (Python)   balance of a new account")
    try:
        print("  BUG ->", SavingsBuggy("Ada", 0.05).balance)
    except AttributeError as e:
        print("  BUG -> AttributeError:", e)
    print("  FIX ->", SavingsFixed("Ada", 0.05).balance)
