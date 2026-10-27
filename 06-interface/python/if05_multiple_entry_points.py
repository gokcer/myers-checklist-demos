"""IF05 · Interface · With several entry points, is a value used that this entry point never set?

Myers' example is PL/I ENTRY. In Python: an alternative constructor (classmethod) that skips __init__.
"""


class PriceBuggy:
    def __init__(self, amount: int, currency: str) -> None:
        self.amount = amount
        self.currency = currency

    @classmethod
    def from_text(cls, text: str) -> "PriceBuggy":
        p = cls.__new__(cls)                # BUG: creates the object WITHOUT __init__ ...
        p.amount = int(text)                # ... and sets only the amount
        return p

    def label(self) -> str:
        return str(self.amount) + " " + self.currency


class PriceFixed:
    def __init__(self, amount: int, currency: str = "TRY") -> None:
        self.amount = amount
        self.currency = currency

    @classmethod
    def from_text(cls, text: str) -> "PriceFixed":
        p = cls(int(text))                  # FIX: every entry point goes through __init__
        return p

    def label(self) -> str:
        return str(self.amount) + " " + self.currency


if __name__ == "__main__":
    print('IF05 Multiple entry points (Python)   Price.from_text("100")')
    try:
        print("  BUG ->", PriceBuggy.from_text("100").label())
    except AttributeError as e:
        print("  BUG -> AttributeError:", e)
    print("  FIX ->", PriceFixed.from_text("100").label())
