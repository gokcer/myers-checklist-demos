"""CP07 · Comparison · Are the assumptions about and / or precedence correct?"""

# spec: discount for MEMBERS who are children (< 12) or seniors (> 65)


def discount_buggy(member: bool, age: int) -> bool:
    return member and age < 12 or age > 65        # BUG: "and" binds first


def discount_fixed(member: bool, age: int) -> bool:
    return member and (age < 12 or age > 65)      # FIX


if __name__ == "__main__":
    print("CP07 Boolean precedence (Python)   non-member, age 70")
    print("  BUG ->", discount_buggy(False, 70))
    print("  FIX ->", discount_fixed(False, 70))
