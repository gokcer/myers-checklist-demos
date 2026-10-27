"""CP05 · Comparison · Are comparison and Boolean operators mixed up?

Python allows 2 < i < 10 (it means what you think!). The trap is "x == a or b": b alone is always truthy.
"""


def is_admin_buggy(role: str) -> bool:
    return role == "admin" or "owner"           # BUG: means (role == "admin") or "owner" -> always truthy


def is_admin_fixed(role: str) -> bool:
    return role == "admin" or role == "owner"   # FIX: a full comparison on each side of "or"


if __name__ == "__main__":
    print("CP05 Boolean operands (Python)   role = 'guest'")
    print("  BUG ->", is_admin_buggy("guest"))
    print("  FIX ->", is_admin_fixed("guest"))
