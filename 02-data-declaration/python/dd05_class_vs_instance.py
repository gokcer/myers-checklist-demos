"""DD05 · Data declaration · Is the initialization consistent with how long the variable lives?

A list written in the class body is created once and shared by every instance.
"""


class CartBuggy:
    items: list[str] = []              # BUG: a class attribute, created once, shared by all carts

    def add(self, item: str) -> None:
        self.items.append(item)


class CartFixed:
    def __init__(self) -> None:
        self.items: list[str] = []     # FIX: created for each cart

    def add(self, item: str) -> None:
        self.items.append(item)


if __name__ == "__main__":
    print("DD05 Class vs instance initialization (Python)   Ada adds an apple; Bob's new cart should be empty")
    ada = CartBuggy()
    ada.add("apple")
    bob = CartBuggy()
    print("  BUG Bob's cart ->", bob.items)
    ada_fixed = CartFixed()
    ada_fixed.add("apple")
    bob_fixed = CartFixed()
    print("  FIX Bob's cart ->", bob_fixed.items)
